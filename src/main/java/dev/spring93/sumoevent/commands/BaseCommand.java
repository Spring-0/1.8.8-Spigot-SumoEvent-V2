package dev.spring93.sumoevent.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand implements CommandExecutor {

    private String commandName;

    public BaseCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(commandName)) {
            if (args.length < getMinArgs() || args.length > getMaxArgs()) {
                sender.sendMessage("Invalid number of arguments!");
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