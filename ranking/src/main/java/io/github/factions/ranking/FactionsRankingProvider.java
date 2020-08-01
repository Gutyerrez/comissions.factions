package io.github.factions.ranking;

import io.github.factions.ranking.cache.local.FactionRankLocalCache;
import io.github.gutyerrez.core.shared.CoreProvider;
import io.github.gutyerrez.core.shared.exceptions.ServiceAlreadyPreparedException;
import io.github.gutyerrez.core.shared.providers.LocalCacheProvider;
import io.github.gutyerrez.core.shared.providers.MysqlDatabaseProvider;

import java.net.InetSocketAddress;

/**
 * @author SrGutyerrez
 */
public class FactionsRankingProvider {

    public static void prepare() throws ServiceAlreadyPreparedException {
        CoreProvider.prepare(
                new MysqlDatabaseProvider(
                        new InetSocketAddress(
                                "172.31.5.54",
                                3306
                        ),
                        "root",
                        "12345",
                        "mariadb"
                )
        );

        Cache.Local.FACTION_RANK.prepare();
    }

    public static class Cache {

        public static class Local {

            public static LocalCacheProvider<FactionRankLocalCache> FACTION_RANK = new LocalCacheProvider<>(
                    new FactionRankLocalCache()
            );

        }

    }

}
