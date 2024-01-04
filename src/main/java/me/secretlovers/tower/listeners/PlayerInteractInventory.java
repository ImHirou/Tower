package me.secretlovers.tower.listeners;

import me.secretlovers.tower.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class PlayerInteractInventory implements Listener {

    private Main main;
    public PlayerInteractInventory(Main main) {
        this.main=main;
    }

    @EventHandler
    public void onEvent(InventoryClickEvent event) {

        if(event.getSlotType().equals(InventoryType.SlotType.ARMOR) &&
        event.getWhoClicked().equals(main.getGameManager().getBoss())) event.setCancelled(true);

    }

}
