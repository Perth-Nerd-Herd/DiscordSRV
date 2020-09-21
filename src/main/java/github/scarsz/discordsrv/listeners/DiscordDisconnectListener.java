/*
 * DiscordSRV - A Minecraft to Discord and back link plugin
 * Copyright (C) 2016-2020 Austin "Scarsz" Shapiro
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package github.scarsz.discordsrv.listeners;

import github.scarsz.discordsrv.DiscordSRV;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.CloseCode;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class DiscordDisconnectListener extends ListenerAdapter {

    @Override
    public void onDisconnect(@NotNull DisconnectEvent event) {
        handleCode(event.getCloseCode());
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {
        handleCode(event.getCloseCode());
    }

    private void handleCode(CloseCode closeCode) {
        if (closeCode == CloseCode.DISALLOWED_INTENTS) {
            Bukkit.getPluginManager().disablePlugin(DiscordSRV.getPlugin()); // make DiscordSRV go red in /plugins
            DiscordSRV.getPlugin().getLogger().severe("==============================================================");
            DiscordSRV.getPlugin().getLogger().severe("");
            DiscordSRV.getPlugin().getLogger().severe(" Your DiscordSRV bot does not have the " + (DiscordSRV.api.getIntents().contains(GatewayIntent.GUILD_PRESENCES) ? "Guild Presences or " : "") + "Server Members Intent!");
            DiscordSRV.getPlugin().getLogger().severe(" DiscordSRV requires this intent to function. Instructions:");
            DiscordSRV.getPlugin().getLogger().severe("  1. Go to https://discord.com/developers/applications");
            DiscordSRV.getPlugin().getLogger().severe("  2. Click on the DiscordSRV bot");
            DiscordSRV.getPlugin().getLogger().severe("  3. Click on \"Bot\" on the left");
            DiscordSRV.getPlugin().getLogger().severe("  4. Enable the \"SERVER MEMBERS INTENT\"");
            DiscordSRV.getPlugin().getLogger().severe("  5. Restart your server");
            DiscordSRV.getPlugin().getLogger().severe("");
            DiscordSRV.getPlugin().getLogger().severe("==============================================================");
        } else if (!closeCode.isReconnect()) {
            Bukkit.getPluginManager().disablePlugin(DiscordSRV.getPlugin()); // make DiscordSRV go red in /plugins
            DiscordSRV.getPlugin().getLogger().severe("===================================================");
            DiscordSRV.getPlugin().getLogger().severe("");
            DiscordSRV.getPlugin().getLogger().severe(" DiscordSRV was disconnected from Discord because:");
            DiscordSRV.getPlugin().getLogger().severe(closeCode == CloseCode.AUTHENTICATION_FAILED ? " The bot token is invalid" : " " + closeCode.getMeaning());
            DiscordSRV.getPlugin().getLogger().severe("");
            DiscordSRV.getPlugin().getLogger().severe("===================================================");
        }
    }
}
