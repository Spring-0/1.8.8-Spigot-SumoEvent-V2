package dev.spring93.sumoevent.listeners;

import dev.spring93.sumoevent.services.GameService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    GameService gameService = new GameService();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        gameService.removePlayerFromQueue(player);

        if(player.equals(gameService.getPlayer1()))
            gameService.endMatch(gameService.getPlayer2(), player);
        else if(player.equals(gameService.getPlayer2()))
            gameService.endMatch(gameService.getPlayer1(), player);
    }

}
