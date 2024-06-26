package dev.spring93.sumoevent.utils;

import dev.spring93.sumoevent.SumoEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class MessageManager {

    private static ConfigManager config = ConfigManager.getInstance();

    public static void broadcastMessage(String msg) {
        Bukkit.broadcastMessage(config.getMessagePrefix() + msg);
    }

    public static void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(config.getMessagePrefix() + msg);
    }

    public static String getHelpMenu() {
        return ChatColor.LIGHT_PURPLE +
                "\n/sumo help > displays this message\n" +
                "/sumo start > starts a sumo event\n" +
                "/sumo stop > stops a sumo event\n" +
                "/sumo join > join a sumo event\n" +
                "/sumo leave > leave a sumo event\n" +
                "/sumo forcestart > force starts a sumo round\n" +
                "/sumo reload > reloads the config\n" +
                "/sumo ver > displays plugin information\n" +
                "/sumo giveflare [player] > gives a player a sumo event starter flare\n";
    }

    public static String getVersionMessage() {
        return ChatColor.LIGHT_PURPLE + "Plugin Name: " + ChatColor.GREEN + "SumoEvent" + "\n" +
                ChatColor.LIGHT_PURPLE + "Author: " + ChatColor.GREEN + "Spring-0" + "\n" +
                ChatColor.LIGHT_PURPLE + "GitHub: " + ChatColor.GREEN + "https://github.com/Spring-0/1.8.8-Spigot-SumoEvent-V2" + "\n" +
                ChatColor.LIGHT_PURPLE + "Spigot: " + ChatColor.GREEN + "https://www.spigotmc.org/" + "\n" +
                ChatColor.LIGHT_PURPLE + "Version: " + ChatColor.GREEN + SumoEvent.getInstance().getDescription().getVersion();
    }

}
