package io.github.gutyerrez.factions.battle.misc.utils;

import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.spigot.misc.utils.old.ItemBuilder;
import io.github.gutyerrez.factions.battle.FactionsBattleProvider;
import org.bukkit.Material;

/**
 * @author SrGutyerrez
 */
public class MPlayerUtils {

    public static ItemBuilder getSkull(MPlayer mPlayer) {
        return new ItemBuilder(Material.SKULL_ITEM)
                .durability(3)
                .name(String.format(
                        "§7[%s] %s",
                        mPlayer.getName(),
                        FactionsBattleProvider.Hooks.CHAT.get().getGroupPrefix(
                                "world",
                                mPlayer.getName()
                        )
                ))
                .lore(
                        String.format("§fPoder: §7%d/%d", mPlayer.getPowerRounded(), mPlayer.getPowerMaxRounded()),
                        String.format("§fCargo: §7%s", mPlayer.getRole().getName()),
                        String.format("§fKDR: §7%s", mPlayer.getKdrRounded()),
                        String.format("§fAbates: §7%d", mPlayer.getKills()),
                        String.format("§fMortes: §7%d", mPlayer.getDeaths()),
                        String.format("§fStatus: %s", mPlayer.isOnline() ? "§aOnline" : "§cOffline")
                );
    }

}
