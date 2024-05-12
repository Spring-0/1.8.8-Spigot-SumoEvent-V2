package dev.spring93.sumoevent.utils;

import dev.spring93.sumoevent.SumoEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ConfigManager {

    private static ConfigManager instance;
    private static FileConfiguration config;

    public ConfigManager() {
        SumoEvent plugin = JavaPlugin.getPlugin(SumoEvent.class);
        plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    public static ConfigManager getInstance() {
        if(instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public String getSumoRegionName() {
        return config.getString("region-name").toLowerCase();
    }

    public Location[] getSumoSpawnLocations(){
        String[] loc1 = config.getString("loc1").split(",");
        String[] loc2 = config.getString("loc2").split(",");

        Location location1 = new Location(Bukkit.getWorld(loc1[0]), Double.parseDouble(loc1[1]), Double.parseDouble(loc1[2]),
                Double.parseDouble(loc1[3]));

        Location location2 = new Location(Bukkit.getWorld(loc2[0]), Double.parseDouble(loc2[1]), Double.parseDouble(loc2[2]),
                Double.parseDouble(loc2[3]));

        location1.setYaw(getYawFromDirection(loc1[4]));
        location2.setYaw(getYawFromDirection(loc2[4]));

        return new Location[] {location1, location2};
    }

    public Location getSumoSpectateWarp() {
        String[] rawLoc = config.getString("sumo-spawn").split(",");
        Location loc = new Location(Bukkit.getWorld(rawLoc[0]),  Double.parseDouble(rawLoc[1]), Double.parseDouble(rawLoc[2]),
                Double.parseDouble(rawLoc[3]));

        loc.setYaw(getYawFromDirection(rawLoc[4]));

        return loc;
    }

    private float getYawFromDirection(String direction) {
        switch(direction.toLowerCase()) {
            case "north":
                return 180;
            case "south":
                return 0;
            case "east":
                return -90;
            case "west":
                return 90;
            default:
                return 0;
        }
    }


    public String getInvalidArgsNumberMessage() {
        return getConfigString("invalid-argument-amount-message");
    }

    public String getInvalidArgMessage() {
        return getConfigString("invalid-argument-message");
    }

    public String getEventStartedMessage() {
        return getConfigString("sumo-event-started-message");
    }

    public String getEventEndedMessage() {
        return getConfigString("sumo-event-ended-message");
    }

    public String getEventAlreadyRunningMessage() {
        return getConfigString("sumo-event-already-running");
    }

    public String getEventNotRunningMessage() {
        return getConfigString("sumo-event-not-running");
    }

    public String getPlayerJoinedEventMessage() {
        return getConfigString("player-joined-sumo-event");
    }

    public String getPlayerLeftEventMessage() {
        return getConfigString("player-left-queue");
    }

    public String getPlayerAlreadyInQueueMessage() {
        return getConfigString("player-already-in-queue");
    }

    public String getPlayerNotInQueueMessage() {
        return getConfigString("player-not-in-queue");
    }

    public String getNotEnoughPlayersMessage() {
        return getConfigString("not-enough-players-message");
    }

    public String getPlayerDefeatedMessage(Player winner, Player loser) {
        return getConfigString("player-has-been-defeated-message")
                .replace("%winner%", winner.getDisplayName())
                .replace("%loser%", loser.getDisplayName());
    }

    public String getEventWinnerMessage(Player winner) {
        return getConfigString("player-won-sumo-event")
                .replace("%winner%", winner.getDisplayName());
    }

    public String getNoPermissionMessage() {
        return getConfigString("no-permission-message");
    }

    public String getMessagePrefix() {
        return getConfigString("message-prefix");
    }

    private String getConfigString(String path) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(path));
    }

    public void reloadConfig(CommandSender sender) {
        SumoEvent plugin = SumoEvent.getInstance();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        MessageManager.sendMessage(sender, "has been reloaded.");
    }

    public List<String> getFinalWinnerRewards() {
        return config.getStringList("rewards.final-winner-rewards");
    }

    public List<String> getFinalSecondPlaceRewards() {
        return config.getStringList("rewards.final-second-place-winner");
    }

    public List<String> getRoundWinnerRewards() {
        return config.getStringList("rewards.round-winner");
    }

    public int getStartMatchDelay() {
        return config.getInt("match-start-delay");
    }

    public boolean getRequireForceStart() {
        return config.getBoolean("require-force-start");
    }

    public String getForceStartReminderMessage() {
        return config.getString("force-start-required-reminder-message");
    }

    public int getMessageBroadcastInterval() {
        return config.getInt("sumo-event-countdown-interval");
    }

    public String getEventBroadcastMessage() {
        return config.getString("event-status-broadcast-message");
    }

    public String getSumoFlareMaterialName() {
        return config.getString("sumo-summoner.material");
    }

    public String getSumoFlareDisplayName() {
        return getConfigString("sumo-summoner.display-name");
    }

    public List<String> getSumoFlareLore() {
        List<String> lore = config.getStringList("sumo-summoner.lore");
        for (int i = 0; i < lore.size(); i++) {
            String line = lore.get(i);
            line = ChatColor.translateAlternateColorCodes('&', line);
            lore.set(i, line);
        }
        return lore;
    }

    public List<String> getSumoFlareCommands() {
        return config.getStringList("sumo-summoner.commands");
    }

    public boolean getSumoFlareGlow() {
        return config.getBoolean("sumo-summoner.glow");
    }

}
