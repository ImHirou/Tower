package me.secretlovers.tower.runnable;

import me.secretlovers.tower.Main;
import me.secretlovers.tower.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PrepareRunnable extends BukkitRunnable {

    private Main main;
    private int countdownTime;
    public PrepareRunnable(Main main) {
        this.main = main;
        countdownTime = main.getGameManager().getCurrentFloor()*5+51;
    }

    @Override
    public void run() {
        countdownTime--;
        if(countdownTime<=0) {
            cancel();
            main.getGameManager().setCurrentGameState(GameState.FIGHTING);
            return;
        }
        else if(countdownTime==60 ||
        countdownTime==50 ||
        countdownTime==40 ||
        countdownTime==30 ||
        countdownTime==15 ||
        countdownTime==10 ||
        countdownTime<=5) sendTimeMessage();



    }

    private void sendTimeMessage() {
        for(Player p : main.getGameManager().getPlayers()) {
            p.sendMessage("ОСТАЛОСЬ " + this.countdownTime + " СЕКУНД");
        }
    }

}


