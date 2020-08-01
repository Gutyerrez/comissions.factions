package io.github.factions.ranking.listeners;

import com.massivecraft.factions.entity.BoardColl;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.massivecore.ps.PS;
import com.sk89q.worldedit.regions.Region;
import io.github.factions.ranking.FactionsRankingConstants;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author SrGutyerrez
 */
public class FactionRankListener implements Listener {

    @EventHandler
    public void on(BlockPlaceEvent event) {
        Block block = event.getBlock();

        Faction faction = BoardColl.get().getFactionAt(PS.valueOf(block));

        if (ArrayUtils.contains(FactionsRankingConstants.BLACK_LISTED_IDS, faction.getId())) {
            return;
        }


    }

}
