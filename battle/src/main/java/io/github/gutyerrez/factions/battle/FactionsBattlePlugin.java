package io.github.gutyerrez.factions.battle;

import io.github.gutyerrez.core.spigot.CustomPlugin;
import lombok.Getter;

/**
 * @author SrGutyerrez
 */
public class FactionsBattlePlugin extends CustomPlugin {

    @Getter
    private static FactionsBattlePlugin instance;

    public FactionsBattlePlugin() {
        FactionsBattlePlugin.instance = this;
    }

    @Override
    public void onEnable() {
    }

}
