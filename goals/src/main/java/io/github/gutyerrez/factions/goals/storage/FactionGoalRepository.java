package io.github.gutyerrez.factions.goals.storage;

import com.google.common.collect.HashBasedTable;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.shared.contracts.storages.repositories.MysqlRepository;
import io.github.gutyerrez.core.shared.providers.MysqlDatabaseProvider;
import io.github.gutyerrez.factions.goals.api.FactionGoal;
import io.github.gutyerrez.factions.goals.storage.specs.CreateFactionGoalTableSpec;
import io.github.gutyerrez.factions.goals.storage.specs.DeleteFactionGoalSpec;
import io.github.gutyerrez.factions.goals.storage.specs.InsertOrUpdateFactionGoalSpec;
import io.github.gutyerrez.factions.goals.storage.specs.SelectAllFactionGoalSpec;

import java.util.UUID;

/**
 * @author SrGutyerrez
 */
public class FactionGoalRepository extends MysqlRepository {

    public FactionGoalRepository(MysqlDatabaseProvider databaseProvider) {
        super(databaseProvider);

        this.createTable();
    }

    private void createTable() {
        this.query(new CreateFactionGoalTableSpec());
    }

    public HashBasedTable<String, UUID, FactionGoal> fetchAll() {
        return this.query(new SelectAllFactionGoalSpec());
    }

    public void insert(MPlayer mPlayer, FactionGoal factionGoal) {
        this.query(new InsertOrUpdateFactionGoalSpec(mPlayer.getName(), mPlayer.getUuid().toString(), mPlayer.getFaction().getId(), factionGoal.getGoal(), factionGoal.getProgress()));
    }

    public Boolean delete(UUID uuid) {
        return this.query(new DeleteFactionGoalSpec(uuid.toString()));
    }

}
