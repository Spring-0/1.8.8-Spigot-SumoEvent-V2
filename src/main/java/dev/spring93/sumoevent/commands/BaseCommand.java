package dev.spring93.sumoevent.commands;

import dev.spring93.sumoevent.utils.ConfigManager;
import dev.spring93.sumoevent.utils.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand implements CommandExecutor {

    private String commandName;
    private final ConfigManager config = ConfigManager.getInstance();

    public BaseCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName)) {
            if (args.length < getMinArgs() || args.length > getMaxArgs()) {
                MessageManager.sendMessage(sender, MessageManager.getHelpMenu());
                return false;
            }
            return execute(sender, args);
        }
        return false;
    }

    protected abstract boolean execute(CommandSender sender, String[] args);

    protected int getMinArgs() {
        return 0;
    }

    protected int getMaxArgs() {
        return Integer.MAX_VALUE;
    }
}
