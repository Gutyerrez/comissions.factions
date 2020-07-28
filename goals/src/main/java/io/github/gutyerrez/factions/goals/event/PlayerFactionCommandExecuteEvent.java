package io.github.gutyerrez.factions.goals.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author SrGutyerrez
 */
@Getter
@RequiredArgsConstructor
public class FactionCommandExecuteEvent extends Event {

    @Getter
    private static HandlerList handlers = new HandlerList();

    private final String commandName;

    private final String[] arguments;

}
