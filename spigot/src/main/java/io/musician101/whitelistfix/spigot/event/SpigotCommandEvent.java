package io.musician101.whitelistfix.spigot.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SpigotCommandEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private final CommandSender commandSender;
    private String command;
    private String[] arguments;

    public SpigotCommandEvent(CommandSender commandSender, String msg)
    {
        this.commandSender = commandSender;
        try
        {
            this.command = msg.substring(0, msg.indexOf(" ")).replace("/", "");
        }
        catch (StringIndexOutOfBoundsException e)
        {
            this.command = msg.replace("/", "");
        }

        try
        {
            this.arguments = msg.replace(command + " ", "").split(" ");
        }
        catch (StringIndexOutOfBoundsException e)
        {
            this.arguments = null;
        }
    }

    public CommandSender getCommandSender()
    {
        return commandSender;
    }

    public String getCommand()
    {
        return command;
    }

    public String[] getArguments()
    {
        return arguments;
    }

    @Override
    public HandlerList getHandlers()
    {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
