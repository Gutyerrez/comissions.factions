package io.github.gutyerrez.factions.battle.api.arena;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class ArenaIcon {

    @JsonProperty("display_name")
    private final String displayName;

    private final String[] lore;

    @JsonProperty("material_data")
    private final MaterialData materialData;

    public ItemStack asItemStack() {
        return new ItemBuilder(this.materialData)
                .name(this.displayName)
                .lore(this.lore)
                .make();
    }

}
