package io.github.gutyerrez.factions.battle.cache.local;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.ImmutableSet;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.factions.battle.api.battle.FactionBattle;

/**
 * @author SrGutyerrez
 */
public class FactionBattleLocalCache implements LocalCache {

    private final Cache<String, FactionBattle> BATTLES = Caffeine.newBuilder()
            .build();

    public FactionBattle get(String factionId) {
        return this.BATTLES.getIfPresent(factionId);
    }

    public void add(FactionBattle factionBattle) {
        this.BATTLES.put(factionBattle.getFactionId(), factionBattle);
    }

    public ImmutableSet<FactionBattle> get() {
        return ImmutableSet.copyOf(this.BATTLES.asMap().values());
    }

}
