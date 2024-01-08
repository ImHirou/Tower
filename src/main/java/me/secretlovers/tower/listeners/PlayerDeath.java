package me.secretlovers.tower.listeners;

import me.secretlovers.tower.Main;
import me.secretlovers.tower.game.GameState;
import me.secretlovers.tower.runnable.EndFightRunnable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerDeath implements Listener {
    private Main main;
    public PlayerDeath(Main main) {
        this.main=main;
    }

    @EventHandler
    public void onEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(player.equals(main.getGameManager().getBoss())) {
            BukkitTask endFightTask = new EndFightRunnable(main).runTaskTimer(main, 20L, 20L);
            main.getArenaManager().teleportBossToFloor(main.getGameManager());
        }
        else if(main.getGameManager().getPlayers().contains(player)) {
            main.getGameManager().getPlayers().remove(player);
            if(main.getGameManager().getPlayers().isEmpty()) {
                main.getGameManager().setCurrentGameState(GameState.FINISH);
            }
        }
    }

}
