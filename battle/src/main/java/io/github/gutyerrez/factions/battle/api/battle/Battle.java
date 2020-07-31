package io.github.gutyerrez.factions.battle.api.battle;

import lombok.*;

import java.util.Date;

/**
 * @author SrGutyerrez
 */
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = { "factionId", "targetId", "status", "createdAt" })
public class Battle {

    @Getter
    @NonNull
    private final String factionId;

    @Getter
    @NonNull
    private final String targetId;

    @Getter
    private Status status;

    @Getter
    @NonNull
    private final Date createdAt;

    @Getter
    private Date updatedAt;

    public void start() {
        this.status = Status.STARTED;


    }

    public enum Status {

        PENDING_REQUEST, WAITING_START, STARTING, STARTED, ENDING, ENDED;

    }

}
