package io.github.gutyerrez.factions.goals.storage.specs;

import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.DeleteSqlSpec;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.specs.PreparedStatementCreator;
import io.github.gutyerrez.factions.goals.FactionsGoalsConstants;
import lombok.RequiredArgsConstructor;

import java.sql.PreparedStatement;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class DeleteFactionGoalSpec extends DeleteSqlSpec<Void> {

    private final String uuidString;

    @Override
    public Void parser(int affectedRows) {
        return null;
    }

    @Override
    public PreparedStatementCreator getPreparedStatementCreator() {
        return connection -> {
            String query = String.format(
                    "DELETE FROM `%s` WHERE `unique_id`=?;",
                    FactionsGoalsConstants.Databases.Mysql.Tables.FACTION_GOAL_TABLE_NAME
            );

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, this.uuidString);

            return preparedStatement;
        };
    }
}
