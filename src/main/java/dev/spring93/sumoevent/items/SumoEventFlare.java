package dev.spring93.sumoevent.items;

import dev.spring93.sumoevent.utils.ConfigManager;
import dev.spring93.sumoevent.utils.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SumoEventFlare {

    private ConfigManager config;
    private static ItemStack flare;

    public SumoEventFlare() {
        config = ConfigManager.getInstance();
        flare = createSumoFlareItem();
    }

    public ItemStack createSumoFlareItem() {
        String materialName = config.getSumoFlareMaterialName();
        String displayName = config.getSumoFlareDisplayName();
        List<String> lore = config.getSumoFlareLore();

        Material material = Material.getMaterial(materialName);
        ItemStack flare = new ItemStack(material);
        ItemMeta meta = flare.getItemMeta();

        meta.setDisplayName(displayName);
        meta.setLore(lore);
        flare.setItemMeta(meta);

        return flare;
    }

    public ItemStack getSumoFlare() {
        return flare;
    }

    public void giveFlareToPlayer(CommandSender sender, String rawTarget) {
        Player target = Bukkit.getPlayerExact(rawTarget);

        if (target == null) {
            sender.sendMessage("Player not found: " + rawTarget);
            return;
        }

        target.getInventory().addItem(getSumoFlare());
        MessageManager.sendMessage(sender, "Gave a flare to " + target.getName() + " .");
        MessageManager.sendMessage(target, "You have received a sumo event flare.");
    }

}
