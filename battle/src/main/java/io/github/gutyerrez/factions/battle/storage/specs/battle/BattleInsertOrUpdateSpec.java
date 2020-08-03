package io.github.gutyerrez.factions.battle.storage.specs.battle;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.InsertSqlSpec;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.factions.battle.api.battle.FactionBattle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author SrGutyerrez
 */
public class BattleInsertOrUpdateSpec extends InsertSqlSpec<FactionBattle> {

    @Override
    public FactionBattle parser(int affectedRows, ResultSet generatedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "INSERT INTO `%s` " +
                            "(" +
                            "``" +
                            ") VALUES (?) ON DUPLICATE KEY UPDATE ``;"
            );

            PreparedStatement preparedStatement = connection.prepareStatement(
                    query,
                    Statement.RETURN_GENERATED_KEYS
            );

            return preparedStatement;
        };
    }
}
