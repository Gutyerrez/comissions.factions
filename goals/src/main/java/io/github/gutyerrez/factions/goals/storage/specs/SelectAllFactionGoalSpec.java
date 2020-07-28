package io.github.gutyerrez.factions.goals.storage.specs;

import com.google.common.collect.HashBasedTable;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.ResultSetExtractor;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.SelectSqlSpec;
import io.github.gutyerrez.factions.goals.FactionsGoalsConstants;
import io.github.gutyerrez.factions.goals.api.FactionGoal;

import java.util.UUID;

/**
 * @author SrGutyerrez
 */
public class SelectAllFactionGoalSpec extends SelectSqlSpec<HashBasedTable<String, UUID, FactionGoal>> {

    @Override
    public ResultSetExtractor<HashBasedTable<String, UUID, FactionGoal>> getResultSetExtractor() {
        return resultSet -> {
            HashBasedTable<String, UUID, FactionGoal> map = HashBasedTable.create();

            do {
                resultSet.next();

                map.put(
                        resultSet.getString("faction_id"),
                        UUID.fromString(
                                resultSet.getString("unique_id")
                        ),
                        new FactionGoal(
                                resultSet.getString("name"),
                                resultSet.getDouble("goal"),
                                resultSet.getDouble("progress")
                        )
                );
            } while (resultSet.next());

            return map;
        };
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "SELECT * FROM `%s`;",
                    FactionsGoalsConstants.Databases.Mysql.Tables.FACTION_GOAL_TABLE_NAME
            );

            return connection.prepareStatement(query);
        };
    }
}
