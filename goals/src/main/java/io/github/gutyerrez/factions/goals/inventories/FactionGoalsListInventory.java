package io.github.gutyerrez.factions.goals.inventories;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import io.github.gutyerrez.core.spigot.inventory.ConfirmInventory;
import io.github.gutyerrez.core.spigot.inventory.PaginateInventory;
import io.github.gutyerrez.core.spigot.misc.utils.ItemBuilder;
import io.github.gutyerrez.factions.goals.FactionsGoalsProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author SrGutyerrez
 */
public class FactionGoalsListInventory extends PaginateInventory {

    public FactionGoalsListInventory(Faction faction) {
        super("Metas da sua facção");

        FactionsGoalsProvider.Cache.Local.FACTION_GOAL.provide().get(faction)
                .forEach((uuid, goal) -> {
                    MPlayer mPlayer = MPlayer.get(uuid);

                    ItemStack skullItem = new ItemBuilder(Material.SKULL_ITEM)
                            .durability(3)
                            .skullOwner(mPlayer.getName())
                            .name(String.format("§7%s %s", mPlayer.getRole().getPrefix() + mPlayer.getRole().getName(), mPlayer.getName()))
                            .make();

                    this.addItem(
                            skullItem,
                            event -> {
                                Player player = (Player) event.getWhoClicked();

                                MPlayer _mPlayer = MPlayer.get(player);

                                if (_mPlayer.getRole() != Rel.LEADER && !_mPlayer.isOverriding()) {
                                    player.closeInventory();

                                    player.sendMessage("§cApenas líderes podem coletar as metas da facção.");
                                    return;
                                }

                                if (event.isLeftClick()) {
                                    player.openInventory(
                                            new FactionGoalEditInventory(mPlayer, goal)
                                    );
                                } else if (event.isRightClick()) {
                                    ConfirmInventory confirmInventory = new ConfirmInventory(
                                            onAccept -> {
                                                if (FactionsGoalsProvider.Hooks.ECONOMY.isActive()) {
                                                    FactionsGoalsProvider.Hooks.ECONOMY.get().depositPlayer(player, goal.getProgress());
                                                }

                                                FactionsGoalsProvider.Cache.Local.FACTION_GOAL.provide().remove(faction, mPlayer);
                                                FactionsGoalsProvider.Repositories.FACTION_GOAL.provide().delete(mPlayer.getUuid());

                                                player.sendMessage(String.format(
                                                        "§aVocê coletou %s coins desta meta e a marcou como concluida.",
                                                        NumberUtils.format(goal.getProgress())
                                                ));

                                                player.openInventory(this);
                                            },
                                            onDeny -> {
                                                // nada
                                            },
                                            skullItem
                                    );

                                    player.openInventory(confirmInventory.make(
                                            "§7Ao confirmar você",
                                            "§7receberá " + NumberUtils.format(goal.getProgress()) + " coins."
                                    ));
                                }
                            }
                    );
                });
    }

}
