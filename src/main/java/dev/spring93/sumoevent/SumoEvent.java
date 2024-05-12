package dev.spring93.sumoevent;

import dev.spring93.sumoevent.commands.SumoCommands;
import dev.spring93.sumoevent.listeners.PlayerQuitListener;
import dev.spring93.sumoevent.listeners.SumoEventFlareUseListener;
import dev.spring93.sumoevent.listeners.SumoListener;
import dev.spring93.sumoevent.services.GameService;
import dev.spring93.sumoevent.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SumoEvent extends JavaPlugin {
    private static SumoEvent instance;

    @Override
    public void onEnable() {
        instance = this;
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
        this.getServer().getPluginManager().registerEvents(new SumoListener(), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        this.getServer().getPluginManager().registerEvents(new SumoEventFlareUseListener(), this);
    }

    public static SumoEvent getInstance() {
        return instance;
    }

}
