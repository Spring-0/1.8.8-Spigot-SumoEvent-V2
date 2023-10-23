package dev.spring93.sumoevent;

import dev.spring93.sumoevent.commands.SumoCommands;
import dev.spring93.sumoevent.listeners.PlayerQuitListener;
import dev.spring93.sumoevent.listeners.SumoListener;
import dev.spring93.sumoevent.services.GameService;
import dev.spring93.sumoevent.utils.ConfigManager;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class SumoEvent extends JavaPlugin {

    private ConfigManager configManager;
    private SumoListener sumoListener;
    private GameService gameService;

    @Override
    public void onEnable() {
        configManager = ConfigManager.getInstance();
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        this.getCommand("sumo").setExecutor(new SumoCommands());
    }

    private void registerListeners() {
        sumoListener = new SumoListener();
        this.getServer().getPluginManager().registerEvents(sumoListener, this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }


}
