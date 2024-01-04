package me.secretlovers.tower.runnable;

import me.secretlovers.tower.Main;
import me.secretlovers.tower.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WaitingCountdown extends BukkitRunnable {
    private Main main;
    public WaitingCountdown(Main main) {
        this.main = main;
    }

    private int countdownTime=31;

    @Override
    public void run() {
        countdownTime--;
        if(countdownTime<=0) {
            cancel();
            main.getGameManager().setCurrentGameState(GameState.STARTING);
            return;
        }
        else if(countdownTime==30 ||
        countdownTime==15 ||
        countdownTime==10 ||
        countdownTime<=5) {
            sendTimeMessage();
        }

    }
    private void sendTimeMessage() {
        for(Player p : main.getGameManager().getPlayers()) {
            p.sendMessage("ОСТАЛОСЬ " + this.countdownTime + " СЕКУНД");
        }
    }

}
