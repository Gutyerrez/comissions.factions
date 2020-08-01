package io.github.gutyerrez.factions.battle.inventories;

import com.google.common.collect.ImmutableList;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.ItemBuilder;
import io.github.gutyerrez.factions.battle.api.battle.FactionBattle;
import io.github.gutyerrez.factions.battle.misc.utils.MPlayerUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
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

    public BattleSelectMembersInventory(Faction faction, FactionBattle factionBattle) {
        super(6 * 9, "Escolha os participantes:");

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
                                factionBattle.isInviter(faction) ? "cancelar" : "recusar"
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
                                factionBattle.isInviter(faction) ? "finalizar" : "aceitar"
                        ))
                        .make()
        );

        ImmutableList<Player> players = ImmutableList.copyOf(
                faction.getOnlinePlayers()
                        .stream()
                        .filter(player -> !factionBattle.isSelected(player))
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

            if (factionBattle.isSelected(player)) {
                continue;
            }

            MPlayer mPlayer = MPlayer.get(player);

            ItemStack skullItem = MPlayerUtils.getSkull(mPlayer)
                    .lore(
                            "",
                            "§aClique para selecionar."
                    )
                    .make();

            this.setItem(
                    memberSelectSlot,
                    skullItem,
                    event -> {
                        Integer slot = event.getSlot();

                        this.setItem(
                                memberSelectSlot,
                                null
                        );

                        factionBattle.select(faction, player);

                        Integer selectedSlot = this.getNextMemberSelectedSlot();

                        this.setItem(
                                selectedSlot,
                                skullItem,
                                event1 -> {
                                    factionBattle.deselect(faction, player);

                                    this.setItem(
                                            selectedSlot,
                                            null
                                    );

                                    this.setItem(
                                            slot,
                                            skullItem
                                    );
                                }
                        );
                    }
            );
        }
    }

    private Integer getNextMemberSelectedSlot() {
        Integer ret = null;

        for (Integer memberSelectedSlot : this.MEMBER_SELECTED_SLOTS) {
            ItemStack item = this.getItem(memberSelectedSlot);

            if (item == null || item.getType() == Material.AIR) {
                ret = memberSelectedSlot;
                break;
            }
        }

        return ret;
    }

}
