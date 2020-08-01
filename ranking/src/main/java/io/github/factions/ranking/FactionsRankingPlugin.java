package io.github.factions.ranking;

import io.github.factions.ranking.listeners.PlayerCommandPreprocessListener;
import io.github.factions.ranking.listeners.FactionRankListener;
import io.github.gutyerrez.core.shared.exceptions.ServiceAlreadyPreparedException;
import io.github.gutyerrez.core.spigot.CustomPlugin;
import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * @author SrGutyerrez
 */
public class FactionsRankingPlugin extends CustomPlugin {

    @Getter
    private static FactionsRankingPlugin instance;

    public FactionsRankingPlugin() {
        FactionsRankingPlugin.instance = this;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        try {
            FactionsRankingProvider.prepare();
        } catch (ServiceAlreadyPreparedException exception) {
            exception.printStackTrace();
        }

        Bukkit.getPluginManager().registerEvents(new FactionRankListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), this);
    }

}
