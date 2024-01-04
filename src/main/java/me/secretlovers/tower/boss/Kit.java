package me.secretlovers.tower.boss;

import me.secretlovers.tower.items.TowerItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Kit {

    private TowerItem helmet;
    private TowerItem chestplate;
    private TowerItem leggings;
    private TowerItem boots;
    private List<TowerItem> weapons = new ArrayList<>();
    private List<TowerItem> eat = new ArrayList<>();
    private List<TowerItem> other = new ArrayList<>();

    public Kit(FileConfiguration kitConfig, String bossName) {
        ConfigurationSection bossToKit = kitConfig.getConfigurationSection(bossName);
        assert bossToKit != null;
        for(String key : bossToKit.getKeys(false)) {
            ConfigurationSection section = bossToKit.getConfigurationSection(key);
            if(key.equals("helmet")) {
                this.helmet = new TowerItem(section);
            }
            else if(key.equals("chestplate")) {
                this.chestplate = new TowerItem(section);
            }
            else if(key.equals("leggings")) {
                this.leggings = new TowerItem(section);
            }
            else if(key.equals("boots")) {
                this.boots = new TowerItem(section);
            }
            else if(key.equals("weapons")) {
                for(String weapon : section.getKeys(false)) {
                    ConfigurationSection weaponSection = section.getConfigurationSection(weapon);
                    this.weapons.add(new TowerItem(weaponSection));
                }
            }
            else if(key.equals("eat")) {
                for(String eat : section.getKeys(false)) {
                    ConfigurationSection eatSection = section.getConfigurationSection(eat);
                    this.eat.add(new TowerItem(eatSection));
                }
            }
            else if(key.equals("other")) {
                for(String other : section.getKeys(false)) {
                    ConfigurationSection otherSection = section.getConfigurationSection(other);
                    this.other.add(new TowerItem(otherSection));
                }
            }
        }

    }

    public void giveToBoss(Player boss) {
        boss.getInventory().clear();
        if(helmet!=null) boss.getInventory().setHelmet(this.helmet.getItemStack());
        if(chestplate!=null) boss.getInventory().setChestplate(this.chestplate.getItemStack());
        if(leggings!=null)boss.getInventory().setLeggings(this.leggings.getItemStack());
        if(boss!=null) boss.getInventory().setBoots(this.boots.getItemStack());
        for (TowerItem weapon : this.weapons) {
            boss.getInventory().addItem(weapon.getItemStack());
        }
        for (TowerItem eatItem : this.eat) {
            boss.getInventory().addItem(eatItem.getItemStack());
        }
        for(TowerItem otherItem : this.other) {
            boss.getInventory().addItem(otherItem.getItemStack());
        }
    }

}
