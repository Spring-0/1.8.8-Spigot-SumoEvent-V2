package dev.spring93.sumoevent.services;

import dev.spring93.sumoevent.SumoEvent;
import dev.spring93.sumoevent.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class GameService {

    private static Queue<Player> playerQueue;
    private static boolean isGameActive = false;
    private static Player player1, player2;
    public static HashSet<Player> frozenPlayers = new HashSet<>();


    public GameService() {
    }

    public void startGame(CommandSender sender) {
        if(!isGameActive) {
            isGameActive = true;
            this.playerQueue = new LinkedList<>();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast Sumo event has started.");
        } else {
            sender.sendMessage("Game is already running.");
        }
    }

    public static void stopGame(CommandSender sender) {
        if(isGameActive) {
            isGameActive = false;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast Sumo event has ended.");
        } else {
            sender.sendMessage("There is no active game.");
        }
    }

    public void addPlayer(Player player) {
        if(informIfGameNotActive(player)) return;

        if(!playerQueue.contains(player)){
            playerQueue.add(player);
            player.sendMessage("You have joined the sumo event.");
            teleportPlayerToSpectatorPlatform(player);
        } else {
            player.sendMessage("You are already in the queue.");
        }
    }

    public void removePlayer(Player player) {
        if(informIfGameNotActive(player)) return;

        if(playerQueue.contains(player)) {
            playerQueue.remove(player);
            player.sendMessage("You have been removed from the sumo event.");
        } else {
            player.sendMessage("You are not in the queue.");
        }
    }

    private boolean informIfGameNotActive(Player player){
        if(!isGameActive) {
            player.sendMessage("There is no active game.");
            return true;
        }
        return false;
    }

    public void startMatch() {
        if(playerQueue.size() < 2) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast Not enough players to start a match.");
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
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + winner.getDisplayName() + " has defeated " +
                loser.getDisplayName() + " on the sumo platform!");

        teleportPlayerToSpectatorPlatform(winner);
        teleportPlayerToSpectatorPlatform(loser);

        PlayerService.restoreInventory(winner);
        PlayerService.restoreInventory(loser);

        playerQueue.add(winner);

        if(playerQueue.size() <= 1) {
            stopGame(Bukkit.getConsoleSender());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "broadcast " + winner.getDisplayName() + " has won the sumo event!");
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

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

}
