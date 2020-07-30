package io.github.gutyerrez.factions.battle;

import io.github.gutyerrez.core.shared.providers.LocalCacheProvider;
import io.github.gutyerrez.factions.battle.cache.local.BattleLocalCache;

/**
 * @author SrGutyerrez
 */
public class FactionsBattleProvider {

    public static class Cache {

        public static class Local {

            public static LocalCacheProvider<BattleLocalCache> BATTLE = new LocalCacheProvider<BattleLocalCache>(
                    new BattleLocalCache()
            );

        }

    }

}
