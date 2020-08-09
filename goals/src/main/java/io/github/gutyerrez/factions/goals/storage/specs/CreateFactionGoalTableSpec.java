package io.github.gutyerrez.factions.goals.storage.specs;

import io.github.gutyerrez.core.shared.storage.repositories.specs.ExecuteSqlSpec;
import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCallback;
import io.github.gutyerrez.core.shared.storage.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.factions.goals.FactionsGoalsConstants;

/**
 * @author SrGutyerrez
 */
public class CreateFactionGoalTableSpec extends ExecuteSqlSpec<Void> {

    @Override
    public PreparedStatementCallback<Void> getPreparedStatementCallback() {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "CREATE TABLE IF NOT EXISTS `%s` " +
                            "(" +
                            "`name` VARCHAR(16) NOT NULL," +
                            "`unique_id` VARCHAR(36) NOT NULL PRIMARY KEY," +
                            "`faction_id` VARCHAR(36) NOT NULL," +
                            "`goal` DOUBLE NOT NULL," +
                            "`progress` DOUBLE" +
                            ");",
                    FactionsGoalsConstants.Databases.Mysql.Tables.FACTION_GOAL_TABLE_NAME
            );

            return connection.prepareStatement(query);
        };
    }
}
