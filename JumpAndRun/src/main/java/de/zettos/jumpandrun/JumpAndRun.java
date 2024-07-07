package de.zettos.jumpandrun;


import de.zettos.jumpandrun.commands.Areas;
import de.zettos.jumpandrun.config.Area;
import de.zettos.jumpandrun.config.Settings;
import de.zettos.jumpandrun.scoreboard.ScoreboardManager;
import de.zettos.jumpandrun.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class JumpAndRun extends JavaPlugin {

    private static JumpAndRun instance;

    private Map<UUID, JumpCore> gameMap;
    private String prefix;

    public static JumpAndRun getInstance(){
        return instance;
    }

    public String getPrefix(){
        return this.prefix;
    }

    public Map<UUID, JumpCore> getGameMap(){
        return this.gameMap;
    }

    @Override
    public void onLoad() {
        instance = this;
        this.gameMap = new HashMap<>();
        this.prefix = "§8[§eJump§8] §7";
    }

    @Override
    public void onEnable() {
        initFile();
        initArea();
        new Listeners();
        new ScoreboardManager();
        new Timer();
        getCommand("area").setExecutor(new Areas());
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(isPlaying(p)) gameMap.get(p.getUniqueId()).stop();
        });
    }

    private void initFile(){
        new Settings(getConfig());
        Settings.getInstance().setDefaults();
        getConfig().options().copyDefaults(true);
        saveConfig();
        Settings.getInstance().init();
    }

    private void initArea(){
        new Area();
        Area.getInstance().init();
    }

    public boolean isPlaying(Player player){
        return gameMap.containsKey(player.getUniqueId());
    }

}
