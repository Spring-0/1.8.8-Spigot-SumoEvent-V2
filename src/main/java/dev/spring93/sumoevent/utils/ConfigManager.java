package dev.spring93.sumoevent.utils;

import dev.spring93.sumoevent.SumoEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private static ConfigManager instance;
    private FileConfiguration config;

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
        return config.getString("region-name");
    }

    public Location[] getSumoSpawnLocations(){
        String[] loc1 = config.getString("loc1").split(",");
        String[] loc2 = config.getString("loc2").split(",");

        Location location1 = new Location(Bukkit.getWorld(loc1[0]), Double.parseDouble(loc1[1]), Double.parseDouble(loc1[2]),
                Double.parseDouble(loc1[3]));

        Location location2 = new Location(Bukkit.getWorld(loc2[0]), Double.parseDouble(loc2[1]), Double.parseDouble(loc2[2]),
                Double.parseDouble(loc2[3]));

        return new Location[] {location1, location2};

    }

}
