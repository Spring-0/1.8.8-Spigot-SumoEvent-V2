package dev.spring93.sumoevent.listeners;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import dev.spring93.sumoevent.services.GameService;
import dev.spring93.sumoevent.utils.ConfigManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SumoListener implements Listener {

    private final ConfigManager configManager = ConfigManager.getInstance();
    private GameService gameService;

    public SumoListener() {
        gameService = new GameService();
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(GameService.frozenPlayers.contains(player)) {
            event.setTo(event.getFrom());
            return;
        }

        if((player.equals(gameService.getPlayer1()) || player.equals(gameService.getPlayer2())) && gameService.isGameActive()) {
            Location from = event.getFrom();
            Location to = event.getTo();

            RegionContainer container = WGBukkit.getPlugin().getRegionContainer();
            RegionQuery query = container.createQuery();

            ApplicableRegionSet fromRegions = query.getApplicableRegions(from);
            ApplicableRegionSet toRegions = query.getApplicableRegions(to);

            String regionName = configManager.getSumoRegionName();

            if(fromRegions.getRegions().stream().anyMatch(region -> region.getId().equals(regionName))
                    && toRegions.size() == 0) {

                Player winner = gameService.getOtherPlayer(player);
                gameService.endMatch(winner, player);

            }
        }
    }

}
