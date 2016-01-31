package musician101.whitelistfix.sponge.listener;

import musician101.whitelistfix.Reference.Commands;
import musician101.whitelistfix.sponge.SpongeWhitelistFix;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.SignSource;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.command.SendCommandEvent;

import javax.annotation.Nonnull;
import java.util.Optional;

public class SpongeCommandListener implements EventListener<SendCommandEvent>
{
	public SpongeCommandListener()
	{

	}
	
	@Override
	public void handle(@Nonnull SendCommandEvent event)
	{
		if (!event.getCommand().equalsIgnoreCase(Commands.WHITELIST))
			return;

        Optional<CommandBlockSource> cbso = event.getCause().first(CommandBlockSource.class);
        Optional<SignSource> sso = event.getCause().first(SignSource.class);
        if (cbso.isPresent() || sso.isPresent())
            return;

        String firstArg = event.getArguments().split(" ")[0];
        if (!firstArg.equalsIgnoreCase(Commands.ON) || !firstArg.equalsIgnoreCase(Commands.RELOAD) || !firstArg.equalsIgnoreCase(Commands.REMOVE))
            return;

        SpongeWhitelistFix.instance.kickNonWhitelistedPlayers();
	}
}
