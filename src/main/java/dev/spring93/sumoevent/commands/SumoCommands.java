package dev.spring93.sumoevent.commands;

import dev.spring93.sumoevent.items.SumoEventFlare;
import dev.spring93.sumoevent.services.GameService;
import dev.spring93.sumoevent.utils.ConfigManager;
import dev.spring93.sumoevent.utils.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SumoCommands extends BaseCommand {

    private GameService gameService;
    private ConfigManager config;

    public SumoCommands() {
        super("sumo");
        gameService = new GameService();
        config = ConfigManager.getInstance();
    }

    @Override
    protected boolean execute(CommandSender sender, String[] args) {
        String arg1 = args[0].toLowerCase().trim();

        switch (arg1) {
            case "start":
                if(sender.hasPermission("sumoevent.command.start"))
                    gameService.startGame(sender);
                else MessageManager.sendMessage(sender, config.getNoPermissionMessage());
                break;
            case "stop":
                if(sender.hasPermission("sumoevent.command.start"))
                    gameService.stopGame(sender);
                else MessageManager.sendMessage(sender, config.getNoPermissionMessage());
                break;
            case "join":
                gameService.addPlayer((Player) sender);
                break;
            case "leave":
                gameService.removePlayer((Player) sender);
                break;
            case "forcestart":
                if(sender.hasPermission("sumoevent.command.forcestart"))
                    gameService.startMatch();
                else MessageManager.sendMessage(sender, config.getNoPermissionMessage());
                break;
            case "reload":
                if(sender.hasPermission("sumoevent.command.reload"))
                    config.reloadConfig(sender);
                else MessageManager.sendMessage(sender, config.getNoPermissionMessage());
                break;
            case "ver":
                if(sender.hasPermission("sumoevent.command.version"))
                    MessageManager.sendMessage(sender, MessageManager.getVersionMessage());
                else MessageManager.sendMessage(sender, config.getNoPermissionMessage());
                break;
            case "giveflare":
                if(sender.hasPermission("sumoevent.command.giveflare"))
                    new SumoEventFlare().giveFlareToPlayer(sender, args[1].toLowerCase().trim());
                else MessageManager.sendMessage(sender, config.getNoPermissionMessage());
                break;
            default:
                if(sender.hasPermission("sumoevent.command.help"))
                    MessageManager.sendMessage(sender, MessageManager.getHelpMenu());
                else MessageManager.sendMessage(sender, config.getNoPermissionMessage());
                break;
        }


        return false;
    }

    @Override
    protected int getMinArgs() {
        return 1;
    }

    @Override
    protected int getMaxArgs() {
        return 2;
    }

}
