package io.github.gutyerrez.factions.goals.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.InsertSqlSpec;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SrGutyerrez
 */
public class InsertOrIncrementFactionGoalSpec extends InsertSqlSpec<Void> {

    @Override
    public Void parser(int affectedRows, ResultSet generatedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "INSERT INTO `%s` " +
                            "(" +
                            "`name`," +
                            "`unique_id`," +
                            "`faction_id`," +
                            "`goal`," +
                            "`progress`" +
                            ")" +
                            " VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE `goal`=VALUES(`goal`), `progress`=VALUES(`progress`)"
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            return preparedStatement;
        };
    }
}
