package io.github.gutyerrez.factions.goals;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.providers.LocalCacheProvider;
import io.github.gutyerrez.core.shared.providers.MysqlRepositoryProvider;
import io.github.gutyerrez.factions.goals.cache.local.FactionGoalLocalCache;
import io.github.gutyerrez.factions.goals.misc.hooks.EconomyHook;
import io.github.gutyerrez.factions.goals.storage.FactionGoalRepository;

/**
 * @author SrGutyerrez
 */
public class FactionsGoalsProvider {

    public static void prepare() {
        Repositories.FACTION_GOAL.prepare();

        Cache.Local.FACTION_GOAL.prepare();

        Hooks.ECONOMY.prepare();
    }

    public static class Hooks {

        public static EconomyHook<?> ECONOMY = new EconomyHook<>();

    }

    public static class Cache {

        public static class Local {

            public static LocalCacheProvider<FactionGoalLocalCache> FACTION_GOAL = new LocalCacheProvider<>(
                    new FactionGoalLocalCache()
            );

        }

    }

    public static class Repositories {

        public static MysqlRepositoryProvider<FactionGoalRepository> FACTION_GOAL = new MysqlRepositoryProvider<>(
                () -> CoreProvider.Database.MySQL.MYSQL_MAIN,
                FactionGoalRepository.class
        );

    }

}
