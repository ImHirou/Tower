package me.secretlovers.tower.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TowerItem {
    private final Material material;
    private final String name;
    private final Map<Enchantment, Integer> enchantmentLevelMap = new HashMap<>();
    private final double chance;
    private final int minAmount;
    private final int maxAmount;
    private final boolean unbreakable;
    private final boolean undroppable;

    public TowerItem(ConfigurationSection section) {
        Material material;

        try {
            material = Material.valueOf(section.getString("material"));
        } catch (Exception e) {
            material = Material.AIR;
        }

        this.material=material;
        this.name = section.getString("name");

        ConfigurationSection enchantmentsSection = section.getConfigurationSection("enchantments");
        if(enchantmentsSection !=null) {
            for(String enchantmentKey : enchantmentsSection.getKeys(false)) {
                Enchantment enchantment = Enchantment.getByKey(
                        NamespacedKey.minecraft(
                                enchantmentKey.toLowerCase()
                        )
                );

                if(enchantment !=null) {
                    int level = enchantmentsSection.getInt(enchantmentKey);
                    this.enchantmentLevelMap.put(enchantment, level);
                }
            }
        }
        if(section.contains("chance")) this.chance = section.getDouble("chance");
        else this.chance=1;
        this.minAmount = section.getInt("minAmount");
        this.maxAmount = section.getInt("maxAmount");
        this.unbreakable = section.getBoolean("unbreakable");
        this.undroppable = section.getBoolean("undroppable");

    }

    public boolean randomize(Random random) {
        return random.nextDouble() < chance;
    }

    public ItemStack getItemStack(ThreadLocalRandom random) {
        int amount = random.nextInt(this.minAmount, this.maxAmount + 1);
        ItemStack itemStack = new ItemStack(this.material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.name !=null) {
            itemMeta.setDisplayName(
                    ChatColor.translateAlternateColorCodes('&', name));
        }
        if(!enchantmentLevelMap.isEmpty()) {
            for(Map.Entry<Enchantment, Integer> enchantmentEntry : this.enchantmentLevelMap.entrySet()) {
                itemMeta.addEnchant(
                        enchantmentEntry.getKey(),
                        enchantmentEntry.getValue(),
                        true
                );
            }
        }
        if(undroppable) itemMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(this.material, this.maxAmount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.name !=null) {
            itemMeta.setDisplayName(
                    ChatColor.translateAlternateColorCodes('&', name));
        }
        if(!enchantmentLevelMap.isEmpty()) {
            for(Map.Entry<Enchantment, Integer> enchantmentEntry : this.enchantmentLevelMap.entrySet()) {
                itemMeta.addEnchant(
                        enchantmentEntry.getKey(),
                        enchantmentEntry.getValue(),
                        true
                );
            }
        }
        if(undroppable) itemMeta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
