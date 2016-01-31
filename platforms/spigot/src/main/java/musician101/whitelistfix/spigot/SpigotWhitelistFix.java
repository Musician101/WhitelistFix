package musician101.whitelistfix.spigot;

import musician101.whitelistfix.spigot.listener.SpigotCommandListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotWhitelistFix extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new SpigotCommandListener(this), this);
		kickNonWhitelistedPlayers();
	}
	
	public void kickNonWhitelistedPlayers()
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
			@Override
			public void run()
			{
				for (Player player : Bukkit.getOnlinePlayers())
					if (!player.isWhitelisted())
						player.kickPlayer("You are not white-listed on this server");
			}
		}, 1L);
	}
}
