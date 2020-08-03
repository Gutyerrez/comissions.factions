package io.github.gutyerrez.factions.battle.api.battle;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import lombok.*;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

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

    @Setter
    @Getter
    private Status status;

    @Getter
    private Set<UUID> factionMembers, targetMembers;

    @Getter
    private Integer factionKills, targetKills, factionDeaths, targetDeaths;

    @Getter
    @NonNull
    private final Date createdAt;

    @Getter
    private Date updatedAt;

    private Set<UUID> confirmed;

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

    public void setConfirmed(MPlayer mPlayer) {
        if (this.confirmed.contains(mPlayer.getUuid())) {
            return;
        }

        this.confirmed.add(mPlayer.getUuid());
    }

    public void start() {
        this.status = Status.STARTED;


    }

    public Boolean isAllConfirmed() {
        AtomicBoolean confirmed = new AtomicBoolean(true);

        // faction
        this.factionMembers.forEach(uuid -> {
            if (!this.confirmed.contains(uuid)) {
                confirmed.set(false);
            }
        });

        // target faction
        this.targetMembers.forEach(uuid -> {
            if (!this.confirmed.contains(uuid)) {
                confirmed.set(false);
            }
        });

        return confirmed.get();
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

        INITIAL_REQUEST, PENDING_REQUEST, WAITING_CONFIRMATION, WAITING_START, STARTING, STARTED, ENDING, ENDED;

    }

}
