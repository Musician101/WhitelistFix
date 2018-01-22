package io.musician101.whitelistfix.spigot.listener;

import io.musician101.whitelistfix.Reference.Commands;
import io.musician101.whitelistfix.spigot.SpigotWhitelistFix;
import io.musician101.whitelistfix.spigot.event.SpigotCommandEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class SpigotCommandListener implements Listener {

    public SpigotCommandListener()//NOSONAR
    {

    }

    @EventHandler
    public void onCommand(SpigotCommandEvent event) {
        CommandSender sender = event.getCommandSender();
        String command = event.getCommand();
        String[] args = event.getArguments();

        if (sender instanceof BlockCommandSender || sender instanceof CommandMinecart) {
            return;
        }

        if (!command.equalsIgnoreCase(Commands.WHITELIST)) {
            return;
        }

        if (args[0].equalsIgnoreCase(Commands.ON) || args[0].equalsIgnoreCase(Commands.RELOAD) || args[0].equalsIgnoreCase(Commands.REMOVE)) {
            SpigotWhitelistFix.instance().kickNonWhitelistedPlayers();
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Bukkit.getServer().getPluginManager().callEvent(new SpigotCommandEvent(event.getPlayer(), event.getMessage()));
    }

    @EventHandler
    public void onCommand(ServerCommandEvent event) {
        Bukkit.getServer().getPluginManager().callEvent(new SpigotCommandEvent(event.getSender(), event.getCommand()));
    }

    @EventHandler
    public void onCommand(RemoteServerCommandEvent event) {
        Bukkit.getServer().getPluginManager().callEvent(new SpigotCommandEvent(event.getSender(), event.getCommand()));
    }
}
