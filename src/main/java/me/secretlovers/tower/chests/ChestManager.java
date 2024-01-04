package me.secretlovers.tower.chests;

import me.secretlovers.tower.Main;
import me.secretlovers.tower.items.TowerItem;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ChestManager implements Listener {
    private final Set<Location> openedChests = new HashSet<>();
    private final List<TowerItem> lootItems = new ArrayList<>();
    private Main main;
    public ChestManager(Main main) {
        this.main = main;
    }

    public void updateLoot(FileConfiguration lootConfig, String lootName) {

        ConfigurationSection section = lootConfig.getConfigurationSection(lootName);
        for(String key : section.getKeys(false)) {
            ConfigurationSection itemSection = section.getConfigurationSection(key);
            lootItems.add(new TowerItem(itemSection));
        }

    }

    @EventHandler
    private void onChestOpen(InventoryOpenEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if(holder instanceof Chest) {
            if(main.getGameManager().getBoss().equals(event.getPlayer())) event.setCancelled(true);
            Chest chest = (Chest) holder;
            if(hasBeenOpened(chest.getLocation())) return;

            markAsOpened(chest.getLocation());
            fillChest(chest.getBlockInventory());
        } else if(holder instanceof DoubleChest) {
            if(main.getGameManager().getBoss().equals(event.getPlayer())) event.setCancelled(true);
            DoubleChest chest = (DoubleChest) holder;
            if(hasBeenOpened(chest.getLocation())) return;

            markAsOpened(chest.getLocation());
            fillChest(chest.getInventory());
        }
    }

    public void fillChest(Inventory inventory) {
        inventory.clear();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        Set<TowerItem> used = new HashSet<>();

        for (int slotIndex = 0; slotIndex < inventory.getSize(); slotIndex++) {
            TowerItem randomItem = lootItems.get(
                    random.nextInt(lootItems.size())
            );
            if(used.contains(randomItem)) continue;
            used.add(randomItem);

            if(randomItem.randomize(random)) {
                ItemStack itemStack = randomItem.getItemStack(random);
                inventory.setItem(slotIndex, itemStack);
            }

        }
    }

    public void markAsOpened(Location location) {
        openedChests.add(location);
    }

    public boolean hasBeenOpened(Location location) {
        return openedChests.contains(location);
    }

    public void resetChests() {
        openedChests.clear();
    }

}
