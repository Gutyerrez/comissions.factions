package io.github.gutyerrez.factions.goals.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author SrGutyerrez
 */
@Getter
@RequiredArgsConstructor
public class PlayerFactionCommandExecuteEvent extends Event implements Cancellable {

    @Getter
    private static HandlerList handlers = new HandlerList();

    private final Player player;
    private final String commandName;
    private final String[] arguments;

    @Setter
    private boolean cancelled;

}
