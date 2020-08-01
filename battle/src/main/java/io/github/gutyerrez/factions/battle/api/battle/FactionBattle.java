package io.github.gutyerrez.factions.battle.api.battle;

import com.massivecraft.factions.entity.Faction;
import lombok.*;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * @author SrGutyerrez
 */
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = { "factionId", "targetId", "status", "createdAt" })
public class FactionBattle {

    @Getter
    @NonNull
    private final String factionId;

    @Getter
    @NonNull
    private final String targetId;

    @Getter
    private Status status;

    private Set<UUID> factionMembers, targetMembers;

    @Getter
    private Integer factionKills, targetKills, factionDeaths, targetDeaths;

    @Getter
    @NonNull
    private final Date createdAt;

    @Getter
    private Date updatedAt;

    public void select(Faction faction, Player player) {
        if (this.factionId.equals(faction.getId())) {
            this.factionMembers.add(player.getUniqueId());
        } else {
            this.targetMembers.add(player.getUniqueId());
        }
    }

    public void deselect(Faction faction, Player player) {
        if (this.factionId.equals(faction.getId())) {
            this.factionMembers.remove(player.getUniqueId());
        } else {
            this.targetMembers.remove(player.getUniqueId());
        }
    }

    public void start() {
        this.status = Status.STARTED;


    }

    public Boolean isSelected(Player player) {
        if (player == null) {
            return false;
        }

        return this.factionMembers.contains(player.getUniqueId())
                || this.targetMembers.contains(player.getUniqueId());
    }

    public Boolean isInviter(Faction faction) {
        return this.factionId.equals(faction.getId());
    }

    public enum Status {

        PENDING_REQUEST, WAITING_START, STARTING, STARTED, ENDING, ENDED;

    }

}
