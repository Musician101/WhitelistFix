package musician101.whitelistfix;

import musician101.whitelistfix.listener.CommandListener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WhitelistFix extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new CommandListener(this), this);
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
