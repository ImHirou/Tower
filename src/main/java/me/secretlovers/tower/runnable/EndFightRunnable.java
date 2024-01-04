package me.secretlovers.tower.runnable;

import me.secretlovers.tower.Main;
import me.secretlovers.tower.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EndFightRunnable extends BukkitRunnable {
    private Main main;
    public EndFightRunnable(Main main) {
        this.main=main;
    }

    private int countdownTime=21;

    @Override
    public void run() {
        countdownTime--;
        if(countdownTime<=0) {
        cancel();
        main.getGameManager().setCurrentFloor(main.getGameManager().getCurrentFloor()+1);
        main.getGameManager().setCurrentGameState(GameState.PREPARING);
        return;
        }
        if(countdownTime==20 ||
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
