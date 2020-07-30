package io.github.gutyerrez.factions.battle.cache.local;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.factions.battle.api.battle.Battle;

/**
 * @author SrGutyerrez
 */
public class BattleLocalCache implements LocalCache {

    private final HashMultimap<String, Battle> BATTLES = HashMultimap.create();

    public ImmutableSet<Battle> get(String factionId) {
        return ImmutableSet.copyOf(this.BATTLES.get(factionId));
    }

    public void add(Battle battle) {
        this.BATTLES.put(battle.getFactionId(), battle);
    }

    public ImmutableSet<Battle> get() {
        return ImmutableSet.copyOf(this.BATTLES.values());
    }

}
