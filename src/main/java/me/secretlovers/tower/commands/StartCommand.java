package me.secretlovers.tower.commands;

import me.secretlovers.tower.Main;
import me.secretlovers.tower.game.GameState;
import me.secretlovers.tower.runnable.WaitingCountdown;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class StartCommand implements CommandExecutor {
    private Main main;
    public StartCommand(Main main) {
        this.main=main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        main.getGameManager().setCurrentGameState(GameState.WAITING);
        for(Player p : Bukkit.getOnlinePlayers()) {
            main.getGameManager().addPlayer(p);
            System.out.println(p);
        }
        BukkitTask startGame = new WaitingCountdown(main).runTaskTimer(main, 20L, 20L);

        return false;
    }
}
