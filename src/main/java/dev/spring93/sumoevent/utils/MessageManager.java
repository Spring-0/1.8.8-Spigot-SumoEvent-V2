package dev.spring93.sumoevent.utils;

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
                "/sumo help > displays this message\n" +
                "/sumo start > starts a sumo event\n" +
                "/sumo stop > stops a sumo event\n" +
                "/sumo join > join a sumo event\n" +
                "/sumo leave > leave a sumo event\n" +
                "/sumo startmatch > force starts a sumo round\n" +
                "/sumo reload > reloads the config\n" +
                "/sumo ver > displays plugin information\n";

    }

}
