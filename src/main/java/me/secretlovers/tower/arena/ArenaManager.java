package me.secretlovers.tower.arena;

import me.secretlovers.tower.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ArenaManager {
    private HashMap<Integer, Location> floorToLocation = new HashMap<>();
    private Location prepareLocation;
    private Location startLocation;

    public ArenaManager(FileConfiguration arenaConfig) {

        for(String key : arenaConfig.getKeys(false)) {
            ConfigurationSection section = arenaConfig.getConfigurationSection(key);
            assert section != null;
            if(key.equals("prepare")) {
                prepareLocation = new Location(Bukkit.getWorld("world"),
                        section.getDouble("x"),
                        section.getDouble("y"),
                        section.getDouble("z"),
                        section.getInt("pitch"),
                        section.getInt("yaw"));
            }
            else if(key.equals("start"))  {
                startLocation = new Location(Bukkit.getWorld("world"),
                        section.getDouble("x"),
                        section.getDouble("y"),
                        section.getDouble("z"),
                        section.getInt("pitch"),
                        section.getInt("yaw"));
            }
            else {
                floorToLocation.put(Integer.valueOf(key),
                        new Location(Bukkit.getWorld("world"),
                        section.getDouble("x"),
                        section.getDouble("y"),
                        section.getDouble("z"),
                        section.getInt("pitch"),
                        section.getInt("yaw")));
            }
        }

    }

    public void teleportBossToFloor(GameManager gm) {
        gm.getBoss().teleport(floorToLocation.get(gm.getCurrentFloor()));
    }

    public void teleportPlayersToFloor(GameManager gm) {
        for(Player p : gm.getPlayers()) p.teleport(floorToLocation.get(gm.getCurrentFloor()));
    }
    public void teleportPlayersToPrepare(GameManager gm) {
        for(Player p : gm.getPlayers()) p.teleport(prepareLocation);
    }
    public void teleportPlayersToStart(GameManager gm) {
        for(Player p : gm.getPlayers()) p.teleport(startLocation);
    }

}
