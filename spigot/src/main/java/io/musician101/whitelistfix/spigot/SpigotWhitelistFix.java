package io.musician101.whitelistfix.spigot;

import io.musician101.whitelistfix.IWhitelistFix;
import io.musician101.whitelistfix.Reference;
import io.musician101.whitelistfix.spigot.listener.SpigotCommandListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotWhitelistFix extends JavaPlugin implements IWhitelistFix
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new SpigotCommandListener(), this);
        kickNonWhitelistedPlayers();
    }

    @Override
    public void kickNonWhitelistedPlayers()
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> Bukkit.getOnlinePlayers().stream().filter(player -> !player.isWhitelisted()).forEach(player -> player.kickPlayer(Reference.KICK_REASON)), 1L);
    }

    public static SpigotWhitelistFix instance()
    {
        return JavaPlugin.getPlugin(SpigotWhitelistFix.class);
    }
}
