package io.github.gutyerrez.factions.battle;

import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.providers.LocalCacheProvider;
import io.github.gutyerrez.core.shared.providers.MysqlRepositoryProvider;
import io.github.gutyerrez.factions.battle.cache.local.FactionBattleLocalCache;
import io.github.gutyerrez.factions.battle.misc.hooks.ChatHook;
import io.github.gutyerrez.factions.battle.storage.BattleRepository;

/**
 * @author SrGutyerrez
 */
public class FactionsBattleProvider {

    public static void prepare() {
        Repositories.BATTLE.prepare();

        Cache.Local.BATTLE.prepare();

        Hooks.CHAT.prepare();
    }

    public static class Hooks {

        public static ChatHook<?> CHAT = new ChatHook<>();

    }

    public static class Cache {

        public static class Local {

            public static LocalCacheProvider<FactionBattleLocalCache> BATTLE = new LocalCacheProvider<FactionBattleLocalCache>(
                    new FactionBattleLocalCache()
            );

        }

    }

    public static class Repositories {

        public static MysqlRepositoryProvider<BattleRepository> BATTLE = new MysqlRepositoryProvider<>(
                () -> CoreProvider.Database.MySQL.MYSQL_MAIN,
                BattleRepository.class
        );

    }

}
