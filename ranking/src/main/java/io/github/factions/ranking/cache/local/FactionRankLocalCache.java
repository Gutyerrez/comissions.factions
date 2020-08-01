package io.github.factions.ranking.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.factions.ranking.api.FactionRank;
import io.github.factions.ranking.api.Rank;
import io.github.gutyerrez.core.shared.cache.LocalCache;

/**
 * @author SrGutyerrez
 */
public class FactionRankLocalCache implements LocalCache {

    private Cache<Rank, FactionRank> CACHE = Caffeine.newBuilder()
            .build();

    public FactionRank get(Rank rank) {
        return this.CACHE.getIfPresent(rank);
    }

    public void update(Rank rank, FactionRank factionRank) {
        this.CACHE.put(rank, factionRank);
    }

}
