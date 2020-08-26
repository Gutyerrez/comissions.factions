package io.github.factions.ranking.inventory;

import io.github.factions.ranking.api.Rank;
import io.github.gutyerrez.core.spigot.inventory.CustomInventory;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class RankingListInventory extends CustomInventory {

    public RankingListInventory() {
        super(3 * 9, "Ranking de facções");

        this.setItem(
                12,
                new ItemBuilder(Material.NETHER_STAR)
                        .name("§aRanking de Valor")
                        .lore(
                                "§7Veja as facções com mais valor",
                                "§7no servidor."
                        )
                        .make(),
                (event) -> {
                    Player player = (Player) event.getWhoClicked();

                    player.openInventory(
                            new RankingViewInventory(Rank.GENERAL)
                    );
                }
        );

        this.setItem(
                14,
                new ItemBuilder(Material.GRASS)
                        .name("§aRanking Geral")
                        .lore(
                                "§7Veja as facções com o melhor",
                                "§7desempenho de modo geral no servidor."
                        )
                        .make(),
                (event) -> {
                    Player player = (Player) event.getWhoClicked();

                    player.openInventory(
                            new RankingViewInventory(Rank.MONEY)
                    );
                }
        );

    }

}
