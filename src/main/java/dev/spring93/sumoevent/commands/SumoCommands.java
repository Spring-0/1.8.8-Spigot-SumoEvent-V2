package dev.spring93.sumoevent.commands;

import dev.spring93.sumoevent.listeners.SumoListener;
import dev.spring93.sumoevent.services.GameService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SumoCommands extends BaseCommand {

    GameService gameService;

    public SumoCommands() {
        super("sumo");
        gameService = new GameService();
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        String arg1 = args[0].toLowerCase().trim();

        switch (arg1) {
            case "start":
                gameService.startGame(sender);
                break;
            case "stop":
                gameService.stopGame(sender);
                break;
            case "join":
                gameService.addPlayer((Player) sender);
                break;
            case "leave":
                gameService.removePlayer((Player) sender);
                break;
            case "startmatch":
                gameService.startMatch();
                break;
            default:
                sender.sendMessage("Invalid argument.");
        }


        return false;
    }

    @Override
    protected int getMinArgs() {
        return 1;
    }

}
