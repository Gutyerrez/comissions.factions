package io.github.gutyerrez.factions.goals.listeners;

import com.google.common.primitives.Doubles;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import io.github.gutyerrez.core.shared.misc.utils.NumberUtils;
import io.github.gutyerrez.factions.goals.FactionsGoalsProvider;
import io.github.gutyerrez.factions.goals.api.FactionGoal;
import io.github.gutyerrez.factions.goals.event.PlayerFactionCommandExecuteEvent;
import io.github.gutyerrez.factions.goals.inventories.FactionGoalsListInventory;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

/**
 * @author SrGutyerrez
 */
public class PlayerListener implements Listener {

    @EventHandler
    public void on(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        String message = event.getMessage();

        if (message.contains(" ")) {
            String[] args = message.split(" ");

            if (!args[0].startsWith("/f")) {
                return;
            }

            PlayerFactionCommandExecuteEvent commandExecuteEvent = new PlayerFactionCommandExecuteEvent(
                    player,
                    args[0].split("/")[1],
                    Arrays.copyOfRange(args, 1, args.length)
            );

            Bukkit.getPluginManager().callEvent(commandExecuteEvent);

            event.setCancelled(commandExecuteEvent.isCancelled());
        }
    }

    @EventHandler
    public void on(PlayerFactionCommandExecuteEvent event) {
        Player player = event.getPlayer();
        MPlayer mPlayer = MPlayer.get(player);

        Faction faction = mPlayer.getFaction();

        if (faction == null || ArrayUtils.contains(new String[]{
                Factions.ID_NONE,
                Factions.ID_SAFEZONE,
                Factions.ID_WARZONE,
        }, faction.getId())) {
            return;
        }

        String[] args = event.getArguments();

        switch (args.length) {
            case 1: {
                if (args[0].equalsIgnoreCase("metas")) {
                    event.setCancelled(true);

                    player.openInventory(
                            new FactionGoalsListInventory(faction)
                    );
                    return;
                }
                break;
            }
            case 2: {
                if (args[0].equalsIgnoreCase("meta")) {
                    event.setCancelled(true);

                    Double goal = Doubles.tryParse(args[1].trim());

                    FactionGoal factionGoal = FactionsGoalsProvider.Cache.Local.FACTION_GOAL.provide().get(faction).get(mPlayer.getUuid());

                    if (factionGoal == null) {
                        player.sendMessage("§cNão foi definido a você nenhuma meta ainda.");
                        return;
                    }

                    if (goal == null || goal.isNaN() || goal <= 0) {
                        player.sendMessage("§cVocê informou um valor inválido.");
                        return;
                    }

                    if (FactionsGoalsProvider.Hooks.ECONOMY.isActive() && FactionsGoalsProvider.Hooks.ECONOMY.get().getBalance(player) < goal) {
                        player.sendMessage("§cVocê não possui tantos coins para fazer isto.");
                        return;
                    }

                    if (FactionsGoalsProvider.Hooks.ECONOMY.isActive()) {
                        FactionsGoalsProvider.Hooks.ECONOMY.get().withdrawPlayer(player, goal);
                    }

                    factionGoal.incrementProgress(goal);

                    FactionsGoalsProvider.Repositories.FACTION_GOAL.provide().insert(
                            mPlayer,
                            factionGoal
                    );

                    player.sendMessage(String.format(
                            "§aVocê adicionou %s coins a sua meta de %s coins.",
                            NumberUtils.format(goal),
                            NumberUtils.format(factionGoal.getGoal())
                    ));
                    return;
                }
                break;
            }
            case 3: {
                if (args[0].equalsIgnoreCase("meta")) {
                    event.setCancelled(true);

                    if (mPlayer.getRole() != Rel.LEADER && !mPlayer.isOverriding()) {
                        player.sendMessage("§cApenas líderes podem definir metas para membros.");
                        return;
                    }

                    String targetName = args[1].trim().toLowerCase();

                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetName);

                    MPlayer _mPlayer = MPlayer.get(offlinePlayer);

                    if (_mPlayer == null) {
                        player.sendMessage("§cEste usuário não existe.");
                        return;
                    }

                    if (_mPlayer.getFaction() == null || ArrayUtils.contains(new String[]{
                            Factions.ID_NONE,
                            Factions.ID_SAFEZONE,
                            Factions.ID_WARZONE,
                    }, _mPlayer.getFaction().getId()) || !faction.getId().equals(_mPlayer.getFaction().getId())) {
                        player.sendMessage("§cEste usuário não pertence a sua facção.");
                        return;
                    }

                    Double goal = Doubles.tryParse(args[2].trim());

                    if (goal == null || goal.isNaN() || goal <= 0) {
                        player.sendMessage("§cVocê informou um valor inválido.");
                        return;
                    }

                    FactionGoal factionGoal = FactionsGoalsProvider.Cache.Local.FACTION_GOAL.provide().get(faction).get(_mPlayer.getUuid());

                    boolean alteredGoal = false;

                    if (factionGoal == null) {
                        factionGoal = new FactionGoal(
                                _mPlayer.getName(),
                                goal
                        );

                        FactionsGoalsProvider.Cache.Local.FACTION_GOAL.provide().add(
                                faction,
                                _mPlayer,
                                factionGoal
                        );

                        alteredGoal = true;
                    } else {
                        factionGoal.setGoal(goal);
                    }

                    FactionsGoalsProvider.Repositories.FACTION_GOAL.provide().insert(
                            _mPlayer,
                            factionGoal
                    );

                    player.sendMessage(String.format(
                            "§aVocê %s meta de %s para %s coins.",
                            alteredGoal ? "atualizou a" : "criou uma",
                            _mPlayer.getName(),
                            NumberUtils.format(goal)
                    ));

                    _mPlayer.msg(String.format(
                            "§a%s %s meta %s %s coins.",
                            player.getName(),
                            alteredGoal ? "atualizou sua" : "criou uma",
                            alteredGoal ? "para" : "para você no valor total de",
                            NumberUtils.format(goal)
                    ));
                    return;
                }
                break;
            }
        }
    }

}
