package io.github.gutyerrez.factions.goals.storage.specs;

import io.github.gutyerrez.core.shared.storage.repositories.specs.InsertSqlSpec;
import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.factions.goals.FactionsGoalsConstants;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class InsertOrUpdateFactionGoalSpec extends InsertSqlSpec<Void> {

    private final String name;
    private final String uuidString;
    private final String factionId;
    private final Double goal;
    private final Double progress;

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
                            " VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE `goal`=VALUES(`goal`), `progress`=VALUES(`progress`);",
                    FactionsGoalsConstants.Databases.Mysql.Tables.FACTION_GOAL_TABLE_NAME
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.name);
            preparedStatement.setString(2, this.uuidString);
            preparedStatement.setString(3, this.factionId);
            preparedStatement.setDouble(4, this.goal);
            preparedStatement.setDouble(5, this.progress);

            return preparedStatement;
        };
    }
}
