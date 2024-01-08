package me.secretlovers.tower.game;

import me.secretlovers.tower.Main;
import me.secretlovers.tower.boss.Boss;
import me.secretlovers.tower.boss.Kit;
import me.secretlovers.tower.runnable.PrepareRunnable;
import me.secretlovers.tower.runnable.StartingCountdown;
import me.secretlovers.tower.runnable.WaitingCountdown;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;

public class GameManager {

    private Main main;
    private GameState currentGameState;
    private int currentFloor;
    private Player boss;
    private ArrayList<Player> fighters = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    public GameManager(Main main) {

        this.main=main;
        currentGameState=GameState.WAITING;
        currentFloor=1;

    }
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void randomizeBoss() {
        if(boss != null) return;
        int rand = new Random().nextInt(players.size());
        boss = players.get(rand);
        fighters = players;
        fighters.remove(boss);
    }

    public Player getBoss() {
        return boss;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getFighters() {
        return fighters;
    }

    /////////////////////////////////////////////////////////////
    //PROCESS GAME
    public void setCurrentGameState(GameState currentGameState) {
        if(this.currentGameState!=GameState.WAITING && currentGameState==GameState.STARTING) return;
        this.currentGameState = currentGameState;
        System.out.println(currentGameState);

        switch (currentGameState) {
            case WAITING:
                processWaiting();
                break;
            case STARTING:
                processStart();
                break;
            case PREPARING:
                processPrepare();
                break;
            case FIGHTING:
                processFight();
                break;
            case FINISH:
                processFinish();
                break;
        }

    }
    private void processWaiting() {
        BukkitTask waitTask = new WaitingCountdown(main).runTaskTimer(main, 20L, 20L);
    }
    private void processStart() {
        randomizeBoss();
        giveKitToBoss(main.getBosses().BossByFloor(currentFloor));
        main.getChestManager().resetChests();
        main.getChestManager().updateLoot(main.getLootConfig().getConfig(), "start");
        main.getArenaManager().teleportPlayersToStart(this);
        main.getArenaManager().teleportBossToFloor(this);
        BukkitTask startTask = new StartingCountdown(main).runTaskTimer(main, 20L, 20L);
    }
    private void processFight() {
        main.getChestManager().resetChests();
        main.getChestManager().updateLoot(main.getLootConfig().getConfig(), String.valueOf(currentFloor));
        main.getArenaManager().teleportPlayersToFloor(this);
    }

    private void processPrepare() {
        main.getArenaManager().teleportPlayersToPrepare(this);
        main.getArenaManager().teleportBossToFloor(this);
        if(currentFloor!=1) giveKitToBoss(main.getBosses().BossByFloor(this.currentFloor));
        BukkitTask prepare = new PrepareRunnable(main).runTaskTimer(main,20L, 20L);
    }

    private void processFinish() {
        main.setGameManager(new GameManager(main));
    }

    private void giveKitToBoss(Boss boss) {
        Kit kit = new Kit(main.getKitConfig().getConfig(), boss.getName());
        kit.giveToBoss(this.boss);
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}
