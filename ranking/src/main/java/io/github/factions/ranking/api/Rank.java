package io.github.factions.ranking.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public enum Rank {
    GENERAL("Valor"),
    MONEY("Coins"),
    GENERATORS("Geradores"),
    POWER("Poder"),
    KDR("KDR");

    @Getter
    private final String name;

    public static void start() {
        for (Rank rank : Rank.values()) {
            switch (rank) {
                case GENERAL: {

                }
            }
        }
    }

    public static Rank get(String name) {
        for (Rank rank : Rank.values()) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }

        return Rank.valueOf(name);
    }

}
