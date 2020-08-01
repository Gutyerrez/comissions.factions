package io.github.gutyerrez.factions.battle.storage;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.MysqlRepository;
import io.github.gutyerrez.core.shared.providers.MysqlDatabaseProvider;

/**
 * @author SrGutyerrez
 */
public class BattleRepository extends MysqlRepository {

    public BattleRepository(MysqlDatabaseProvider databaseProvider) {
        super(databaseProvider);
    }

}
