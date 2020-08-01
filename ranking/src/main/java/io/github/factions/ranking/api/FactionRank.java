package io.github.factions.ranking.api;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * @author SrGutyerrez
 */
@RequiredArgsConstructor
public class FactionRank {

    private final Map<Integer, ItemStack> factions = Maps.newHashMap();

    public ItemStack get(Integer position) {
        return this.factions.getOrDefault(position, null);
    }

}
