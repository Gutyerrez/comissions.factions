package io.github.gutyerrez.factions.battle.inventories;

import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.spigot.inventory.ConfirmInventory;
import io.github.gutyerrez.factions.battle.api.battle.FactionBattle;
import org.bukkit.entity.Player;

/**
 * @author SrGutyerrez
 */
public class BattleConfirmInventory extends ConfirmInventory {

    public BattleConfirmInventory(FactionBattle factionBattle) {
        super(
                onAccept -> {
                    Player player = (Player) onAccept.getWhoClicked();

                    MPlayer mPlayer = MPlayer.get(player);

                    factionBattle.setConfirmed(mPlayer);

                    player.closeInventory();
                },
                onDeny -> {
                    // nada
                },
                null
        );
    }

}
