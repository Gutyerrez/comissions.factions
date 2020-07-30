package io.github.gutyerrez.factions.goals.cache.local;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.shared.cache.LocalCache;
import io.github.gutyerrez.factions.goals.api.FactionGoal;

import java.util.UUID;

/**
 * @author SrGutyerrez
 */
public class FactionGoalLocalCache implements LocalCache {

    private final HashBasedTable<String, UUID, FactionGoal> CACHE = HashBasedTable.create();

    public ImmutableMap<UUID, FactionGoal> get(Faction faction) {
        return this.get(faction.getId());
    }

    public ImmutableMap<UUID, FactionGoal> get(String factionId) {
        return ImmutableMap.copyOf(this.CACHE.row(factionId));
    }

    public void add(Faction faction, MPlayer mPlayer, FactionGoal factionGoal) {
        this.add(faction.getId(), mPlayer.getUuid(), factionGoal);
    }

    public void add(String factionId, UUID playerUUID, FactionGoal factionGoal) {
        this.CACHE.put(factionId, playerUUID, factionGoal);
    }

    public void remove(Faction faction, MPlayer mPlayer) {
        this.remove(faction.getId(), mPlayer.getUuid());
    }

    public void remove(String factionId, UUID uuid) {
        this.CACHE.remove(factionId, uuid);
    }

}
