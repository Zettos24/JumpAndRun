package de.zettos.jumpandrun.config;

import de.zettos.jumpandrun.JumpAndRun;
import org.bukkit.Location;

import java.text.MessageFormat;

public class Area {

    private static Area instance;

    public Area() {
        instance = this;
    }

    private boolean area;
    private Location position1;
    private Location position2;

    public static Area getInstance(){
        return instance;
    }

    public void setPosition1(Location position1){
        this.position1 = position1;
        Settings.getInstance().setPosition(position1, 1);
    }

    public void setPosition2(Location position2){
        this.position2 = position2;
        Settings.getInstance().setPosition(position2, 2);
    }

    public Location getPosition1(){
        return this.position1;
    }
    public Location getPosition2(){
        return this.position2;
    }

    public boolean hasArea(){
        return this.area;
    }

    public boolean save(){
        if(position1 != null && position2 != null){
            Settings.getInstance().setStartHight(Math.min(position1.getBlockY(), position2.getBlockY()) + 5);
            Settings.getInstance().setEndHight(Math.max(position1.getBlockY(), position2.getBlockY()) - 5);
            JumpAndRun.getInstance().saveConfig();
            area = true;
            return true;
        }
        return false;
    }

    public String toPositionString(Location position, String name){
        if(position != null && name != null){
            return MessageFormat.format(name+" [x: {0}, y: {1}, z: {2}]",position.getBlockX(), position.getBlockY(), position.getBlockZ());
        }
        return null;
    }

    @Override
    public String toString() {
        return "Area{" +
                "area=" + area +
                ", position1=" + position1 +
                ", position2=" + position2 +
                '}';
    }

    public void init(){
        if(!JumpAndRun.getInstance().getConfig().contains("Settings.area")){
            area = false;
            return;
        }
        this.position1 = (Location) JumpAndRun.getInstance().getConfig().get("Settings.area.position1");
        this.position2 = (Location) JumpAndRun.getInstance().getConfig().get("Settings.area.position2");
        area = true;
    }

    public Location randomLocation(int y, int puffer){
        int maxX = Math.max(position1.getBlockX(), position2.getBlockX())-puffer;
        int minX = Math.min(position1.getBlockX(), position2.getBlockX())+puffer;

        int maxZ = Math.max(position1.getBlockZ(), position2.getBlockZ())-puffer;
        int minZ = Math.min(position1.getBlockZ(), position2.getBlockZ())+puffer;

        int rX = (int) ((Math.random() * (maxX - minX)) + minX);
        int rZ = (int) ((Math.random() * (maxZ - minZ)) + minZ);

        Location loc = new Location(position1.getWorld(), rX, y, rZ);
        return loc;
    }

    public boolean isInArea(Location loc){
        if(!hasArea()){
            return false;
        }

        Location locA = position1;
        Location locB = position2;

        int maxX = (int) Math.max(locA.getX(), locB.getX());
        int minX = (int) Math.min(locA.getX(), locB.getX());

        int maxY = (int) Math.max(locA.getY(), locB.getY());
        int minY = (int) Math.min(locA.getY(), locB.getY());

        int maxZ = (int) Math.max(locA.getZ(), locB.getZ());
        int minZ = (int) Math.min(locA.getZ(), locB.getZ());

        if(loc.getX() <= maxX && loc.getX() >= minX){
            if(loc.getY() <= maxY && loc.getY() >= minY){
                if(loc.getZ() <= maxZ && loc.getZ() >= minZ){
                    return true;
                }
            }
        }
        return false;
    }

}
