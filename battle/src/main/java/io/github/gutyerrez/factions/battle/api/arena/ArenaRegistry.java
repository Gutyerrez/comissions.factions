package io.github.gutyerrez.factions.battle.api.arena;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author SrGutyerrez
 */
public class ArenaRegistry {

    private static final Map<String, Arena> ARENAS = Maps.newHashMap();

    public static void register(Arena arena) {
        ArenaRegistry.ARENAS.put(
                arena.getName(),
                arena
        );
    }

    public static Arena get(String name) {
        return ArenaRegistry.ARENAS.get(name);
    }

    public static ImmutableMap<String, Arena> get() {
        return ImmutableMap.copyOf(ArenaRegistry.ARENAS);
    }

}
