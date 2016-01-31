package musician101.whitelistfix.sponge;

import musician101.whitelistfix.Reference;
import musician101.whitelistfix.sponge.listener.SpongeCommandListener;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.whitelist.WhitelistService;
import org.spongepowered.api.text.Text;

import java.util.Optional;

@Plugin(id = Reference.ID, name = Reference.NAME, version = Reference.VERSION)
public class SpongeWhitelistFix
{
    public static SpongeWhitelistFix instance;

    @Listener
    public void preInit(GameStartedServerEvent event)
    {
        instance = this;
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
        Task.builder().execute(new Runnable()
        {
            @Override
            public void run()
            {
                for (Player player : Sponge.getServer().getOnlinePlayers())
                    if (!whitelistService.isWhitelisted(player.getProfile()))
                        player.kick(Text.of(Reference.KICK_REASON));
            }
        }).delayTicks(1L).name(Reference.NAME + "-KickDelay").submit(Sponge.getPluginManager().getPlugin(Reference.ID));
    }
}
