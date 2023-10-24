package dev.spring93.sumoevent.services;

import dev.spring93.sumoevent.SumoEvent;
import dev.spring93.sumoevent.utils.ConfigManager;
import dev.spring93.sumoevent.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameService {

    private static Queue<Player> playerQueue;
    private static boolean isGameActive = false;
    private static Player player1, player2;
    public static HashSet<Player> frozenPlayers = new HashSet<>();
    private ConfigManager config;


    public GameService() {
        config = ConfigManager.getInstance();
    }

    public void startGame(CommandSender sender) {
        if(!isGameActive) {
            isGameActive = true;
            this.playerQueue = new LinkedList<>();
            MessageManager.broadcastMessage(config.getEventStartedMessage());

            if(!config.getRequireForceStart())
                new SchedulerService(this).scheduleStartMatch();
            else MessageManager.sendMessage(sender, config.getForceStartReminderMessage());

        } else {
            MessageManager.sendMessage(sender, config.getEventAlreadyRunningMessage());
        }
    }

    public void stopGame(CommandSender sender) {
        if(isGameActive) {
            isGameActive = false;
            MessageManager.broadcastMessage(config.getEventEndedMessage());
        } else {
            MessageManager.sendMessage(sender, config.getEventNotRunningMessage());
        }
    }

    public void addPlayer(Player player) {
        if(informIfGameNotActive(player)) return;

        if(!playerQueue.contains(player)){
            playerQueue.add(player);
            MessageManager.sendMessage(player, config.getPlayerJoinedEventMessage());
            teleportPlayerToSpectatorPlatform(player);
        } else {
            MessageManager.sendMessage(player, config.getPlayerAlreadyInQueueMessage());
        }
    }

    public void removePlayer(Player player) {
        if(informIfGameNotActive(player)) return;

        if(playerQueue.contains(player)) {
            playerQueue.remove(player);
            MessageManager.sendMessage(player, config.getPlayerLeftEventMessage());
        } else {
            MessageManager.sendMessage(player, config.getPlayerNotInQueueMessage());
        }
    }

    private boolean informIfGameNotActive(Player player){
        if(!isGameActive) {
            MessageManager.sendMessage(player, config.getEventNotRunningMessage());
            return true;
        }
        return false;
    }

    public void startMatch() {
        if(playerQueue.size() < 2) {
            MessageManager.broadcastMessage(config.getNotEnoughPlayersMessage());
            return;
        }

        this.player1 = playerQueue.poll();
        this.player2 = playerQueue.poll();

        PlayerService.saveAndClearInventory(this.player1);
        PlayerService.saveAndClearInventory(this.player2);

        PlayerService.clearPotionEffects(this.player1);
        PlayerService.clearPotionEffects(this.player2);

        // Teleport the players to the arena;
        Location[] spawnLocations = ConfigManager.getInstance().getSumoSpawnLocations();
        this.player1.teleport(spawnLocations[0]);
        this.player2.teleport(spawnLocations[1]);

        frozenPlayers.add(player1);
        frozenPlayers.add(player2);

        // Countdown
        new BukkitRunnable() {
            int countdownTime = 3;

            @Override
            public void run() {
                if(countdownTime > 0) {
                    PlayerService.playAnvilSound(player1);
                    PlayerService.playAnvilSound(player2);
                    countdownTime--;
                } else {
                    frozenPlayers.remove(player1);
                    frozenPlayers.remove(player2);
                    cancel();
                }
            }
        }.runTaskTimer(SumoEvent.getInstance(), 0L, 20L);

    }

    public void endMatch(Player winner, Player loser) {
        MessageManager.broadcastMessage(config.getPlayerDefeatedMessage(winner, loser));

        teleportPlayerToSpectatorPlatform(winner);
        teleportPlayerToSpectatorPlatform(loser);

        PlayerService.restoreInventory(winner);
        PlayerService.restoreInventory(loser);

        giveRewards(winner, config.getRoundWinnerRewards());

        playerQueue.add(winner);

        if(playerQueue.size() <= 1) {
            stopGame(Bukkit.getConsoleSender());
            MessageManager.broadcastMessage(config.getEventWinnerMessage(winner));
            giveRewards(winner, config.getFinalWinnerRewards());
            giveRewards(loser, config.getFinalSecondPlaceRewards());
        } else {
            startMatch();
        }
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public Player getOtherPlayer(Player player) {
        if(player.equals(player1)) return player2;
        else if(player.equals(player2)) return player1;
        else return null;
    }

    public void teleportPlayerToSpectatorPlatform(Player player) {
        player.teleport(ConfigManager.getInstance().getSumoSpectateWarp());
    }

    public static void removePlayerFromQueue(Player player) {
        if(playerQueue.contains(player)) {
            playerQueue.remove(player);
        }
    }

    private void giveRewards(Player winner, List<String> rewards) {
        for( String reward : rewards) {
            reward = reward.replace("%winner%", winner.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
        }
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getPlayerCount() {
        return playerQueue.size();
    }

}
