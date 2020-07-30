package io.github.gutyerrez.factions.battle.api.battle;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

/**
 * @author SrGutyerrez
 */
@AllArgsConstructor
public class Battle {

    @Getter
    private final String factionId;
    private final String targetId;
    private final Date createdAt;

    private Date updatedAt;

    public enum Status {

        PENDING_REQUEST, WAITING_START, STARTING, STARTED, ENDING, ENDED;

    }

}
