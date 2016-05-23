package io.musician101.whitelistfix.sponge;

import io.musician101.whitelistfix.IWhitelistFix;
import io.musician101.whitelistfix.Reference;
import io.musician101.whitelistfix.sponge.listener.SpongeCommandListener;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.whitelist.WhitelistService;
import org.spongepowered.api.text.Text;

import java.util.Optional;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION, description = Reference.DESCRIPTION)
public class SpongeWhitelistFix implements IWhitelistFix
{
    @Listener
    public void preInit(GameStartedServerEvent event)//NOSONAR
    {
        Sponge.getEventManager().registerListener(this, SendCommandEvent.class, new SpongeCommandListener());
        kickNonWhitelistedPlayers();
    }

    public void kickNonWhitelistedPlayers()
    {
        Server server = Sponge.getServer();
        if (!server.hasWhitelist())
            return;

        Optional<WhitelistService> whitelistServiceOptional = Sponge.getServiceManager().provide(WhitelistService.class);
        if (!whitelistServiceOptional.isPresent())
            return;

        WhitelistService whitelistService = whitelistServiceOptional.get();
        Task.builder().execute(() -> Sponge.getServer().getOnlinePlayers().stream().filter(player -> !whitelistService.isWhitelisted(player.getProfile())).forEach(player -> player.kick(Text.of(Reference.KICK_REASON)))).delayTicks(1L).name(Reference.NAME + "-KickDelay").submit(Sponge.getPluginManager().getPlugin(Reference.ID));
    }

    public static SpongeWhitelistFix instance()
    {
        //noinspection OptionalGetWithoutIsPresent
        return (SpongeWhitelistFix) Sponge.getPluginManager().getPlugin(Reference.ID).get();
    }
}
