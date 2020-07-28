package io.github.gutyerrez.factions.goals;

import io.github.gutyerrez.core.spigot.CustomPlugin;
import io.github.gutyerrez.factions.goals.listeners.AsyncPlayerChatListener;
import io.github.gutyerrez.factions.goals.listeners.PlayerListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

/**
 * @author SrGutyerrez
 */
public class FactionsGoalsPlugin extends CustomPlugin {

    @Getter
    private static FactionsGoalsPlugin instance;

    public FactionsGoalsPlugin() {
        FactionsGoalsPlugin.instance = this;
    }

    @Override
    public void onEnable() {
        FactionsGoalsProvider.prepare();

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), this);
    }

}
