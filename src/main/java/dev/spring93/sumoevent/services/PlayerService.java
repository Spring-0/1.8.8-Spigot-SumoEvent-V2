package dev.spring93.sumoevent.services;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class PlayerService {

    private static HashMap<UUID, ItemStack[]> inventories = new HashMap<>();
    private static HashMap<UUID, ItemStack[]> armorInventories = new HashMap<>();

    public static void saveAndClearInventory(Player player) {
        inventories.put(player.getUniqueId(), player.getInventory().getContents());
        armorInventories.put(player.getUniqueId(), player.getInventory().getArmorContents());

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

    public static void restoreInventory(Player player) {
        ItemStack[] inventory = inventories.get(player.getUniqueId());
        if(inventory != null) {
            player.getInventory().setContents(inventory);
            inventories.remove(player.getUniqueId());
        }

        ItemStack[] armorInventory = armorInventories.get(player.getUniqueId());
        if(armorInventory != null) {
            player.getInventory().setArmorContents(armorInventory);
            armorInventories.remove(player.getUniqueId());
        }
    }

    public static void clearPotionEffects(Player player) {
        for(PotionEffectType potionEffect : PotionEffectType.values()) {
            if(potionEffect != null)
                player.removePotionEffect(potionEffect);
        }
    }

    public static void playAnvilSound(Player player) {
        player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
    }

}
