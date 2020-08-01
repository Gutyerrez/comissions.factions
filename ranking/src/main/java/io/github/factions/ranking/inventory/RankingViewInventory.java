package io.github.factions.ranking.inventory;

import io.github.factions.ranking.FactionsRankingProvider;
import io.github.factions.ranking.api.FactionRank;
import io.github.factions.ranking.api.Rank;
import io.github.gutyerrez.core.spigot.inventory.PaginateInventory;
import org.bukkit.inventory.ItemStack;

/**
 * @author SrGutyerrez
 */
public class RankingViewInventory extends PaginateInventory {

    public RankingViewInventory(Rank rank) {
        super(String.format("Ranking Geral - %s", rank.getName()));

        FactionRank factionRank = FactionsRankingProvider.Cache.Local.FACTION_RANK.provide().get(rank);

        for (int i = 0; i < 200; i++) {
            ItemStack itemStack = factionRank.get(i);

            if (itemStack == null) continue;

            this.addItem(
                    itemStack
            );
        }
    }

}
