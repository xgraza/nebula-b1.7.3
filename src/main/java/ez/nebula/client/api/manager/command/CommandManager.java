package ez.nebula.client.api.manager.command;

import ez.nebula.client.api.listener.EventBus;
import ez.nebula.client.api.listener.EventListener;
import ez.nebula.client.api.listener.Subscribe;
import ez.nebula.client.api.listener.event.EventKey;
import ez.nebula.client.api.logger.Logger;
import ez.nebula.client.api.manager.ITypedManager;
import ez.nebula.client.impl.command.FriendCommand;
import ez.nebula.client.impl.command.GiveBlockCommand;
import ez.nebula.client.impl.command.ToggleCommand;
import ez.nebula.client.impl.command.VClipCommand;
import ez.nebula.client.impl.gui.command.ConsoleGUI;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.util.*;

public final class CommandManager implements ITypedManager<Command>
{
    private static final Minecraft MC = Minecraft.getMinecraft();

    private final Map<String, Command> commandAliasMap = new LinkedHashMap<>();
    private final List<Command> commandList = new ArrayList<>();

    @Subscribe
    private final EventListener<EventKey> keyEventListener = event ->
    {
        if (MC.currentScreen != null)
        {
            return;
        }
        if (event.getKeyCode() == Keyboard.KEY_PERIOD)
        {
            MC.displayGuiScreen(new ConsoleGUI(this));
        }
    };

    @Override
    public void init()
    {
        EventBus.subscribe(this);

        registerCommand(new FriendCommand());
        registerCommand(new GiveBlockCommand());
        registerCommand(new ToggleCommand());
        registerCommand(new VClipCommand());
    }

    private void registerCommand(final Command command)
    {
        commandList.add(command);
        for (final String alias : command.getManifest().aliases())
        {
            commandAliasMap.put(alias, command);
        }
    }

    public void parseCommand(final String text, final List<String> returnText)
    {
        final List<String> args = getArgs(text);
        if (args.isEmpty())
        {
            addCommandList(returnText);
            return;
        }
        final String commandName = args.get(0).toLowerCase();
        final Command command = commandAliasMap.get(commandName);
        if (command == null)
        {
            returnText.add("No command found with name \"" + commandName + "\"");
            return;
        }
        args.remove(0);
        if (args.isEmpty())
        {
            returnText.add(command.getManifest().syntax());
        }
        try
        {
            returnText.clear();
            returnText.add(command.dispatch(args));
        } catch (final Exception e)
        {
            Logger.error("Failed to dispatch command %s", commandName, e);
            returnText.add(e.getMessage());
        }
    }

    public void addCommandList(final List<String> returnText)
    {
        for (final Command command : commandList)
        {
            final StringJoiner joiner = new StringJoiner(" ");
            joiner.add("[" + String.join("|", command.getManifest().aliases()) + "]");
            joiner.add(command.getManifest().syntax());
            joiner.add("-");
            joiner.add(command.getManifest().description());
            returnText.add(joiner.toString());
        }
    }

    public List<String> getArgs(String input)
    {
        if (input.startsWith("."))
        {
            input = input.substring(1);
        }

        final List<String> argList = new ArrayList<>();
        final char[] chars = input.toCharArray();

        String currentArg = "";
        boolean startedQuote = false;
        for (final char c : chars)
        {
            if (c == ' ')
            {
                argList.add(currentArg);
                currentArg = "";
                continue;
            } else if (c == '"' || c == '\'')
            {
                if (startedQuote)
                {
                    startedQuote = false;
                    argList.add(currentArg);
                    currentArg = "";
                    continue;
                }
                startedQuote = true;
            }
            currentArg += c;
        }
        if (!currentArg.isEmpty())
        {
            argList.add(currentArg);
        }
        return argList;
    }

    @Override
    public Collection<Command> getAll()
    {
        return commandList;
    }
}
