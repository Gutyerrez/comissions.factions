package io.github.gutyerrez.factions.battle.misc.hooks;

import io.github.gutyerrez.core.shared.misc.hooks.Hook;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

/**
 * @author SrGutyerrez
 */
public class ChatHook<C extends Chat> extends Hook<C> {

    @Override
    public C prepare() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return null;
        }

        RegisteredServiceProvider<Chat> serviceProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);

        if (serviceProvider == null) {
            return null;
        }

        return this.setInstance((C) serviceProvider.getProvider());
    }
}
