package io.github.gutyerrez.factions.goals.listeners;

import io.github.gutyerrez.factions.goals.event.FactionCommandExecuteEvent;
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

            if (!args[0].startsWith("/f")) {
                return;
            }

            FactionCommandExecuteEvent commandExecuteEvent = new FactionCommandExecuteEvent(
                    args[0].split("/")[1],
                    Arrays.copyOfRange(args, 1, args.length)
            );
        }
    }

}
