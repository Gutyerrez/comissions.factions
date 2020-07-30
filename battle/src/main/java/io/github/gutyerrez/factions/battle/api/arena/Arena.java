package io.github.gutyerrez.factions.battle.api.arena;

import io.github.gutyerrez.core.shared.world.location.SerializedLocation;
import io.github.gutyerrez.core.spigot.CoreSpigotConstants;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class Arena {

    @Getter
    private final String name;

    @Getter
    private final ArenaIcon icon;

    private final SerializedLocation serializedLocation;
    private final Boolean enabled;

    public Location getLocation() {
        return this.serializedLocation.parser(CoreSpigotConstants.LOCATION_PARSER);
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

}
