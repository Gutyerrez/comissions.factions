package io.github.gutyerrez.factions.goals.api;

import lombok.*;

/**
 * @author SrGutyerrez
 */
@Getter
@ToString
@AllArgsConstructor
@EqualsAndHashCode(of = { "owner" })
public class FactionGoal {

    @NonNull
    private final String owner;

    @Setter
    @NonNull
    private Double goal;

    private Double progress;

    public FactionGoal(String owner, Double goal) {
        this(owner, goal, 0.0);
    }

    public void incrementProgress(Double progress) {
        this.progress += progress;
    }

    public Double getProgressRemaining() {
        return this.goal - this.progress;
    }

    public Boolean isCompleted() {
        return this.progress >= this.goal;
    }

}
