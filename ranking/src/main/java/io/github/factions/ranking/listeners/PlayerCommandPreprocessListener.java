package io.github.factions.ranking.listeners;

import io.github.factions.ranking.api.Rank;
import io.github.factions.ranking.inventory.RankingListInventory;
import io.github.factions.ranking.inventory.RankingViewInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

/**
 * @author SrGutyerrez
 */
public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    public void on(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage();

        if (message.contains(" ")) {
            String[] args = message.split(" ");

            if (args.length < 1) {
                return;
            }

            if (args[1].equalsIgnoreCase("ranking")) {
                event.setCancelled(true);

                switch (args.length) {
                    case 2: {
                        player.openInventory(
                                new RankingListInventory()
                        );
                        return;
                    }
                    case 3: {
                        Rank rank = Rank.get(args[2]);

                        player.openInventory(
                                new RankingViewInventory(rank)
                        );
                        return;
                    }
                }
            }
        }
    }

}
