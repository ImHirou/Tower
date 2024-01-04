package me.secretlovers.tower;

import me.secretlovers.tower.arena.ArenaConfig;
import me.secretlovers.tower.arena.ArenaManager;
import me.secretlovers.tower.boss.BossPool;
import me.secretlovers.tower.boss.KitConfig;
import me.secretlovers.tower.chests.ChestManager;
import me.secretlovers.tower.commands.StartCommand;
import me.secretlovers.tower.game.GameManager;
import me.secretlovers.tower.items.LootConfig;
import me.secretlovers.tower.listeners.PlayerDeath;
import me.secretlovers.tower.listeners.PlayerDropItem;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private ChestManager chestManager;
    private ArenaManager arenaManager;
    private GameManager gameManager;
    private final LootConfig lootConfig = new LootConfig(this);
    private final ArenaConfig arenaConfig = new ArenaConfig(this);
    private final KitConfig kitConfig = new KitConfig(this);
    private final BossPool bosses = new BossPool();
    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        setupConfigs();
        bosses.init();

        arenaManager = new ArenaManager(arenaConfig.getConfig());
        gameManager = new GameManager(this);


        chestManager = new ChestManager(this);
        getCommand("start").setExecutor(new StartCommand(this));
        getServer().getPluginManager().registerEvents(chestManager, this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItem(this), this);
    }

    private void setupConfigs() {
        lootConfig.setup();
        lootConfig.getConfig().addDefault("Message", "enabled");
        lootConfig.getConfig().options().copyDefaults();
        lootConfig.save();

        arenaConfig.setup();
        arenaConfig.getConfig().addDefault("Message", "enabled");
        arenaConfig.getConfig().options().copyDefaults();
        arenaConfig.save();

        kitConfig.setup();
        kitConfig.getConfig().addDefault("Message", "enabled");
        kitConfig.getConfig().options().copyDefaults();
        kitConfig.save();
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public ChestManager getChestManager() {
        return chestManager;
    }

    public LootConfig getLootConfig() {
        return lootConfig;
    }

    public BossPool getBosses() {
        return bosses;
    }

    public ArenaConfig getArenaConfig() {
        return arenaConfig;
    }

    public KitConfig getKitConfig() {
        return kitConfig;
    }
}
