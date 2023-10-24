package dev.spring93.sumoevent.services;

import dev.spring93.sumoevent.SumoEvent;
import dev.spring93.sumoevent.utils.ConfigManager;
import dev.spring93.sumoevent.utils.MessageManager;
import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerService {

    private GameService gameService;
    private long delay;
    private ConfigManager config = ConfigManager.getInstance();

    public SchedulerService(GameService gameService) {
        this.gameService = gameService;
    }


    public void scheduleStartMatch() {
        int startMatchDelay = config.getStartMatchDelay();
        int messageInterval = config.getMessageBroadcastInterval();

        new BukkitRunnable() {
            int countdownTime = startMatchDelay;

            @Override
            public void run() {
                if(countdownTime > 0) {
                    if(countdownTime % messageInterval == 0) { // If countdownTime is a multiple of the interval
                        String broadcastMessage = config.getEventBroadcastMessage()
                                .replace("%time_left%", Integer.toString(countdownTime / 60))
                                .replace("%player_count%", Integer.toString(gameService.getPlayerCount()));
                        MessageManager.broadcastMessage(broadcastMessage);
                    }
                    countdownTime--;
                } else {
                    gameService.startMatch();
                    cancel();
                }
            }
        }.runTaskTimer(SumoEvent.getInstance(), 0L, 20L);
    }


}
