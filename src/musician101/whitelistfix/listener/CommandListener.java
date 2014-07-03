package musician101.whitelistfix.listener;

import musician101.whitelistfix.WhitelistFix;
import musician101.whitelistfix.event.CommandEvent;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener
{
	public WhitelistFix plugin;
	
	public CommandListener(WhitelistFix plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCommand(CommandEvent event)
	{
		CommandSender sender = event.getSender();
		String command = event.getCommand();
		String[] args = event.getArguments();
		
		if (sender instanceof BlockCommandSender || sender instanceof CommandMinecart)
			return;
		
		if (!command.equalsIgnoreCase("whitelist"))
			return;
		
		if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("remove"))
			plugin.kickNonWhitelistedPlayers();
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event)
	{
		Bukkit.getServer().getPluginManager().callEvent(new CommandEvent(event.getPlayer(), event.getMessage()));
	}
	
	@EventHandler
	public void onCommand(ServerCommandEvent event)
	{
		Bukkit.getServer().getPluginManager().callEvent(new CommandEvent(event.getSender(), event.getCommand()));
	}
	
	@EventHandler
	public void onCommand(RemoteServerCommandEvent event)
	{
		Bukkit.getServer().getPluginManager().callEvent(new CommandEvent(event.getSender(), event.getCommand()));
	}
}
