package io.musician101.whitelistfix.spigot;

import io.musician101.musicianlibrary.java.minecraft.spigot.plugin.AbstractSpigotPlugin;
import io.musician101.whitelistfix.Reference;
import io.musician101.whitelistfix.spigot.listener.SpigotCommandListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SpigotWhitelistFix extends AbstractSpigotPlugin
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new SpigotCommandListener(), this);
        kickNonWhitelistedPlayers();
    }

    public void kickNonWhitelistedPlayers()
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> Bukkit.getOnlinePlayers().stream().filter(player -> !player.isWhitelisted()).forEach(player -> player.kickPlayer(Reference.KICK_REASON)), 1L);
    }

    public static SpigotWhitelistFix instance()
    {
        return JavaPlugin.getPlugin(SpigotWhitelistFix.class);
    }
}
