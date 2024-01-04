package me.secretlovers.tower.listeners;

import me.secretlovers.tower.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItem implements Listener {

    private Main main;
    public PlayerDropItem(Main main) {
        this.main=main;
    }

    @EventHandler
    public void onEvent(PlayerDropItemEvent event) {
        if (main.getGameManager().getBoss().equals(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

}
