package me.secretlovers.tower.arena;

import me.secretlovers.tower.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArenaConfig {
    private Main main;
    public ArenaConfig(Main main) {
        this.main = main;
    }
    private File file;
    private FileConfiguration customFile;
    public void setup(){
        file = new File(main.getDataFolder(), "arenaconfig.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                System.out.println(e.fillInStackTrace());
            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig(){
        return customFile;
    }

    public void save(){
        try{
            customFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public void reload(){
        customFile = YamlConfiguration.loadConfiguration(file);
    }


}
