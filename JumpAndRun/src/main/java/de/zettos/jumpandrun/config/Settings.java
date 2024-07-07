package de.zettos.jumpandrun.config;

import de.zettos.jumpandrun.JumpAndRun;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

    private static Settings instance;

    private final FileConfiguration config;

    public Settings(FileConfiguration config) {
        instance = this;
        this.config = config;
    }

    public static Settings getInstance(){
        return instance;
    }

    private Material block;
    private Material undergroundBlock;
    private int undergroundHight;
    private int stackoverflowAttempts;
    private int startHight;
    private int endHight;

    public Material getBlock() {
        return block;
    }

    public void setBlock(Material block) {
        this.block = block;
        config.addDefault("Settings.activition.block", block.name());
        JumpAndRun.getInstance().saveConfig();
    }

    public Material getUndergroundBlock() {
        return undergroundBlock;
    }

    public void setUndergroundBlock(Material undergroundBlock) {
        this.undergroundBlock = undergroundBlock;
        config.addDefault("Settings.activition.underground.block", undergroundBlock.name());
        JumpAndRun.getInstance().saveConfig();
    }

    public int getUndergroundHight() {
        return undergroundHight;
    }

    public void setUndergroundHight(int undergroundHight) {
        this.undergroundHight = undergroundHight;
        config.addDefault("Settings.activition.underground.hight", undergroundHight);
        JumpAndRun.getInstance().saveConfig();
    }

    public int getStackoverflowAttempts() {
        return stackoverflowAttempts;
    }

    public void setStackoverflowAttempts(int stackoverflowAttempts) {
        this.stackoverflowAttempts = stackoverflowAttempts;
        config.addDefault("Settings.stackoverflowAttempts", stackoverflowAttempts);
        JumpAndRun.getInstance().saveConfig();
    }

    public void setStartHight(int startHight) {
        this.startHight = startHight;
        config.addDefault("Settings.startHight", startHight);
        JumpAndRun.getInstance().saveConfig();
    }
    public int getStartHight() {
        return startHight;
    }

    public int getEndHight() {
        return endHight;
    }

    public void setEndHight(int endHight) {
        this.endHight = endHight;
        config.addDefault("Settings.endHight", endHight);
        JumpAndRun.getInstance().saveConfig();
    }

    public void setDefaults(){
        setStackoverflowAttempts(100);
        setStartHight(100);
        setEndHight(110);
        setBlock(Material.GOLD_PLATE);
        setUndergroundBlock(Material.REDSTONE_BLOCK);
        setUndergroundHight(2);
    }

    public void init(){
        this.stackoverflowAttempts = config.getInt("Settings.stackoverflowAttempts");
        this.startHight = config.getInt("Settings.startHight");
        this.endHight = config.getInt("Settings.endHight");
        this.block = Material.getMaterial(config.getString("Settings.activition.block"));
        this.undergroundBlock = Material.getMaterial(config.getString("Settings.activition.underground.block"));
        this.undergroundHight = config.getInt("Settings.activition.underground.hight");
    }

    protected void setPosition(Location loc, int pos){
        config.addDefault("Settings.area.position"+pos, loc);
    }

}
