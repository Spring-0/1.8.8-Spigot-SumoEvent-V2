package dev.spring93.sumoevent.listeners;

import dev.spring93.sumoevent.items.SumoEventFlare;
import dev.spring93.sumoevent.services.GameService;
import dev.spring93.sumoevent.utils.ConfigManager;
import dev.spring93.sumoevent.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SumoEventFlareUseListener implements Listener {

    private ConfigManager config;
    private GameService gameService;

    public SumoEventFlareUseListener() {
        config = ConfigManager.getInstance();
        gameService = new GameService();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        ItemStack sumoFlare = new SumoEventFlare().getSumoFlare();

        if(item != null && item.isSimilar(sumoFlare) && event.getAction().name().contains("RIGHT")) {

            if(gameService.isGameActive()) {
                event.setCancelled(true);
                MessageManager.sendMessage(player, config.getEventAlreadyRunningMessage());
                return;
            }

            ItemStack singleFlare = sumoFlare.clone();
            singleFlare.setAmount(1);
            player.getInventory().removeItem(singleFlare);
            List<String> commands = config.getSumoFlareCommands();

            gameService.startGame(player);
            for (String command : commands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
            }
            event.setCancelled(true);
        }

    }

}
