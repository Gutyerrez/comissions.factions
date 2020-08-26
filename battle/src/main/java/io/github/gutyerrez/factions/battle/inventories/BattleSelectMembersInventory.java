package io.github.gutyerrez.factions.battle.inventories;

import com.google.common.collect.ImmutableList;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import io.github.gutyerrez.factions.battle.FactionsBattlePlugin;
import io.github.gutyerrez.factions.battle.api.battle.FactionBattle;
import io.github.gutyerrez.factions.battle.misc.utils.MPlayerUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author SrGutyerrez
 */
public class BattleSelectMembersInventory extends CustomInventory {

    private final Integer[] MEMBER_SELECT_SLOTS = {
            9, 10, 11, 12,
            18, 19, 20, 21,
            27, 28, 29, 30,
            36, 37, 38, 39,
            43, 46, 47, 48
    };

    private final Integer[] MEMBER_SELECTED_SLOTS = {
            14, 15, 16, 17,
            23, 24, 25, 26,
            32, 33, 34, 35,
            41, 42, 43, 44,
            50, 51, 52, 53
    };

    private final FactionBattle factionBattle;

    public BattleSelectMembersInventory(Faction faction, FactionBattle factionBattle) {
        super(6 * 9, "Escolha os participantes:");

        this.factionBattle = factionBattle;

        for (int i = 0; i < this.getSize(); i++) {
            if (ArrayUtils.contains(this.MEMBER_SELECT_SLOTS, i) || ArrayUtils.contains(this.MEMBER_SELECTED_SLOTS, i))
                continue;

            this.setItem(
                    i,
                    new ItemBuilder(Material.STAINED_GLASS_PANE)
                            .name("§eSeparador")
                            .make()
            );
        }

        this.setItem(
                0,
                new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .durability(14)
                        .name("§cCancelar")
                        .lore(String.format(
                                "§7Clique para %s o convite de batalha.",
                                this.factionBattle.isInviter(faction) ? "cancelar" : "recusar"
                        ))
                        .make()
        );

        this.setItem(
                8,
                new ItemBuilder(Material.STAINED_GLASS_PANE)
                        .durability(14)
                        .name("§aAceitar")
                        .lore(String.format(
                                "§7Clique para %s o convite de batalha.",
                                this.factionBattle.isInviter(faction) ? "finalizar" : "aceitar"
                        ))
                        .make(),
                event -> {
                    CustomInventory confirmInventory = new BattleConfirmInventory(this.factionBattle)
                            .make(
                                    "§7Teste..."
                            );

                    switch (this.factionBattle.getStatus()) {
                        case INITIAL_REQUEST: {
                            this.factionBattle.setStatus(FactionBattle.Status.PENDING_REQUEST);

                            this.factionBattle.getFactionMembers().forEach(uuid -> {
                                Player _player = Bukkit.getPlayer(uuid);

                                _player.openInventory(confirmInventory);
                            });

                            // enviar mensagem para facção desafiada selecionar os membros
                            break;
                        }
                        case PENDING_REQUEST: {
                            this.factionBattle.setStatus(FactionBattle.Status.PENDING_REQUEST);

                            this.factionBattle.getTargetMembers().forEach(uuid -> {
                                Player _player = Bukkit.getPlayer(uuid);

                                _player.openInventory(confirmInventory);
                            });

                            // avisar que foi confirmado e em alguns minutos irá iniciar a batalha
                            break;
                        }
                    }

                    Bukkit.getScheduler().runTaskLater(
                            FactionsBattlePlugin.getInstance(),
                            () -> {
                                if (confirmInventory.getViewers().size() > 0) {
                                    this.factionBattle.setStatus(FactionBattle.Status.ENDED);

                                    confirmInventory.getViewers().forEach(HumanEntity::closeInventory);
                                }
                            },
                            20L * 60 * 3
                    );
                }
        );

        ImmutableList<Player> players = ImmutableList.copyOf(
                faction.getOnlinePlayers()
                        .stream()
                        .filter(player -> !this.factionBattle.isSelected(player))
                        .collect(Collectors.toList())
        );

        AtomicInteger index = new AtomicInteger(0);

        for (Integer memberSelectSlot : this.MEMBER_SELECT_SLOTS) {
            if (index.get() >= players.size()) {
                break;
            }

            Player player = players.get(index.getAndIncrement());

            if (player == null) {
                continue;
            }

            if (this.factionBattle.isSelected(player)) {
                continue;
            }

            MPlayer mPlayer = MPlayer.get(player);

            this.setMemberSkullItem(memberSelectSlot, mPlayer);
        }
    }

    private Integer getMemberDeselectedSlot() {
        Integer slot = null;

        for (Integer memberSelectedSlot : this.MEMBER_SELECT_SLOTS) {
            ItemStack item = this.getItem(memberSelectedSlot);

            if (item == null || item.getType() == Material.AIR) {
                slot = memberSelectedSlot;
                break;
            }
        }

        return slot;
    }

    private Integer getNextMemberSelectedSlot() {
        Integer slot = null;

        for (Integer memberSelectedSlot : this.MEMBER_SELECTED_SLOTS) {
            ItemStack item = this.getItem(memberSelectedSlot);

            if (item == null || item.getType() == Material.AIR) {
                slot = memberSelectedSlot;
                break;
            }
        }

        return slot;
    }

    private void setMemberSkullItem(Integer memberSlot, MPlayer mPlayer) {
        ItemStack skullItem = MPlayerUtils.getSkull(mPlayer)
                .lore(
                        "",
                        String.format("§aClique para %s.", this.factionBattle.isSelected(mPlayer.getPlayer()) ? "deselecionar" : "selecionar")
                )
                .make();

        this.setItem(
                memberSlot,
                skullItem,
                event -> {
                    Integer slot = event.getSlot();

                    this.setItem(
                            slot,
                            null
                    );

                    Integer _memberSlot;

                    Player player = mPlayer.getPlayer();
                    Faction faction = mPlayer.getFaction();

                    if (this.factionBattle.isSelected(player)) {
                        factionBattle.select(faction, player);

                        _memberSlot = this.getNextMemberSelectedSlot();
                    } else {
                        factionBattle.deselect(faction, player);

                        _memberSlot = this.getMemberDeselectedSlot();
                    }

                    this.setMemberSkullItem(
                            _memberSlot,
                            mPlayer
                    );
                }
        );
    }

}
