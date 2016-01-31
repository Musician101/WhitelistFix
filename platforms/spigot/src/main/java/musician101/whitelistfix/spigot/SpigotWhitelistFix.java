package musician101.whitelistfix.spigot;

import musician101.whitelistfix.Reference;
import musician101.whitelistfix.spigot.listener.SpigotCommandListener;
import org.bukkit.Bukkit;
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
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> Bukkit.getOnlinePlayers().stream().filter(player -> !player.isWhitelisted()).forEach(player -> player.kickPlayer(Reference.KICK_REASON)), 1L);
    }
}
