package io.github.gutyerrez.factions.goals.inventories;

import com.google.common.primitives.Doubles;
import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import io.github.gutyerrez.core.spigot.inventory.ConfirmInventory;
import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import io.github.gutyerrez.factions.goals.FactionsGoalsProvider;
import io.github.gutyerrez.factions.goals.api.FactionGoal;
import io.github.gutyerrez.factions.goals.listeners.AsyncPlayerChatListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class FactionGoalEditInventory extends CustomInventory {

    public FactionGoalEditInventory(MPlayer mPlayer, FactionGoal factionGoal) {
        super(4 * 9, String.format("Editar meta de %s", mPlayer.getName()));

        this.setItem(
                13,
                new ItemBuilder(Material.SKULL_ITEM)
                        .durability(3)
                        .skullOwner(mPlayer.getName())
                        .name("§7" + mPlayer.getName())
                        .make()
        );

        this.setItem(
                20,
                new ItemBuilder(Material.BOOK_AND_QUILL)
                        .name("§aEditar meta")
                        .make(),
                event -> {
                    Player player = (Player) event.getWhoClicked();

                    player.closeInventory();

                    if (factionGoal.isCompleted()) {
                        player.sendMessage("§cVocê não pode alterar uma meta que já está completa.");
                        return;
                    }
                    
                    player.sendMessage("§aInsira a nova meta desejada abaixo.");
                    player.sendMessage("§7Caso queira cancelar digite 'cancelar'.");

                    AsyncPlayerChatListener.on(
                            player,
                            onChat -> {
                                onChat.setCancelled(true);

                                String message = onChat.getMessage();

                                if (message.equalsIgnoreCase("cancelar")) {
                                    player.sendMessage("§cVocê cancelou a alteração com sucesso!");
                                    return;
                                }

                                Double goal = Doubles.tryParse(message);

                                if (goal == null || goal.isNaN() || goal <= 0) {
                                    player.sendMessage("§cVocê informou uma meta inválida.");
                                    return;
                                }

                                factionGoal.setGoal(goal);

                                FactionsGoalsProvider.Repositories.FACTION_GOAL.provide().insert(
                                        mPlayer,
                                        factionGoal
                                );

                                player.sendMessage(String.format(
                                        "§aVocê atualizou a meta de %s para %s coins.",
                                        mPlayer.getName(),
                                        NumberUtils.format(goal)
                                ));

                                mPlayer.msg(String.format(
                                        "§a%s atualizou sua meta para %s coins.",
                                        player.getName(),
                                        NumberUtils.format(goal)
                                ));
                            }
                    );
                });

        this.setItem(
                24,
                new ItemBuilder(Material.LAVA_BUCKET)
                        .name("§cExcluir meta")
                        .make(),
                event -> {
                    Player player = (Player) event.getWhoClicked();

                    MPlayer _mPlayer = MPlayer.get(player);

                    ConfirmInventory confirmInventory = new ConfirmInventory(
                            onAccept -> {
                                player.closeInventory();

                                FactionsGoalsProvider.Cache.Local.FACTION_GOAL.provide().remove(
                                        _mPlayer.getFaction(),
                                        mPlayer
                                );
                                FactionsGoalsProvider.Repositories.FACTION_GOAL.provide().delete(
                                        mPlayer.getUuid()
                                );

                                mPlayer.msg(String.format(
                                        "§cO %s %s deletou sua meta.",
                                        _mPlayer.getRole().getName().toLowerCase(),
                                        _mPlayer.getName()
                                ));
                            },
                            onDeny -> {
                                // nada
                            },
                            new ItemBuilder(Material.SKULL_ITEM)
                                    .durability(3)
                                    .skullOwner(mPlayer.getName())
                                    .name(String.format("§7Meta do %s %s", mPlayer.getRole().getPrefix() + mPlayer.getRole().getName(), mPlayer.getName()))
                                    .make()
                    );

                    player.openInventory(
                            confirmInventory.make(
                                    "§7Ao confirmar você",
                                    "§7irá deletar esta meta."
                            )
                    );
                }
        );
    }

}
