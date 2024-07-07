package de.zettos.jumpandrun;


import de.zettos.jumpandrun.config.Area;
import de.zettos.jumpandrun.config.Settings;
import de.zettos.jumpandrun.scoreboard.ScoreboardManager;
import de.zettos.jumpandrun.timer.Timer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Random;

public class JumpCore {

    private final int startHight;
    private final int endHight;

    private final Random RAND;

    private final Player player;

    private final DyeColor color;

    private boolean running;

    public JumpCore(Player player) {
        this.player = player;
        this.running = false;
        this.RAND = new Random();
        this.color = DyeColor.values()[RAND.nextInt(DyeColor.values().length)];

        this.rounds = 1;
        this.jumps = 0;
        this.startHight = Settings.getInstance().getStartHight();
        this.endHight = Settings.getInstance().getEndHight();
    }

    private Location currentBlock;
    private Location nextBlock;

    private int rounds;
    private int jumps;


    public void start(){
        if(running || JumpAndRun.getInstance().isPlaying(player)) {
            return;
        }

        player.sendMessage(JumpAndRun.getInstance().getPrefix()+"Game §astarted§7.");
        Timer.getInstance().startTimer(player);
        createSpawn();
        running = true;
        JumpAndRun.getInstance().getGameMap().put(player.getUniqueId(), this);
    }

    public void running(){
        if(!running){
            return;
        }

        //Next Block
        Location pl = player.getLocation();
        if(pl.getBlockX() == nextBlock.getBlockX() && pl.getBlockY() - 1 == nextBlock.getBlockY()
                && pl.getBlockZ() == nextBlock.getBlockZ()){

            //Next Round
            if(nextBlock.getBlockY() >= endHight){
                nextRound();
                ScoreboardManager.getInstance().updateBoard(player, rounds, jumps);
                return;
            }

            currentBlock.getBlock().setType(Material.AIR);
            currentBlock = nextBlock;

            nextBlock = getNextLocation(nextBlock);

            setWoolBlock(nextBlock);
            setClayBlock(currentBlock);
            player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 2, 2);
            jumps++;
            ScoreboardManager.getInstance().updateBoard(player, rounds, jumps);
        }

        //Lose
        if(pl.getBlockY() < currentBlock.getBlockY()){
            stop();
            player.sendMessage(JumpAndRun.getInstance().getPrefix()+"Game §cstopped§7.");
        }

        //Area check
        if(Area.getInstance().hasArea()){
            if(!Area.getInstance().isInArea(pl)){
                stop();
                player.sendMessage(JumpAndRun.getInstance().getPrefix()+"Game §cstopped§7. §cYou moved outside the game-area!");
            }
        }

    }

    public void stop(){
        if(running){
            running = false;
            if(currentBlock != null)
                currentBlock.getBlock().setType(Material.AIR);
            if(nextBlock != null)
                nextBlock.getBlock().setType(Material.AIR);
            JumpAndRun.getInstance().getGameMap().remove(player.getUniqueId());
            player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 1, 2);
            ScoreboardManager.getInstance().removeScoreboard(player);
            Timer.getInstance().stopTimer(player);
        }
    }

    private void nextRound(){
        if(running){
            createSpawn();
            rounds++;
            player.sendMessage(JumpAndRun.getInstance().getPrefix()+"Round §c" + rounds + " §abeginning§7.");
        }
    }

    private void createSpawn(){

        if(currentBlock != null)
            currentBlock.getBlock().setType(Material.AIR);
        if(nextBlock != null)
            nextBlock.getBlock().setType(Material.AIR);

        Location playerLoc = player.getLocation();

        if(Area.getInstance().hasArea()){
            this.currentBlock = Area.getInstance().randomLocation(startHight,5);
        }else{
            this.currentBlock = new Location(playerLoc.getWorld(), RAND.nextBoolean() ? playerLoc.getBlockX() + RAND.nextInt(10)+10 : playerLoc.getBlockX() - RAND.nextInt(10)+10,
                    startHight,
                    RAND.nextBoolean() ? playerLoc.getBlockZ() + RAND.nextInt(10)+10 : playerLoc.getBlockZ() - RAND.nextInt(10)+10);
        }

        this.nextBlock = getNextLocation(currentBlock);

        setClayBlock(currentBlock);
        setWoolBlock(nextBlock);

        currentBlock.add(0,1,0);
        player.teleport(currentBlock);
        currentBlock.subtract(0,1,0);

        ScoreboardManager.getInstance().updateBoard(player,rounds,jumps);

    }

    private Location getNextLocation(Location currentBlock){

        int x = Math.max(RAND.nextInt(6) - 2, 0);
        int z = Math.max(RAND.nextInt(6) - 2, 0);
        Location loc;

        int[] cords = modifyCords(x,z);

        x = cords[0];
        z = cords[1];

        loc = new Location(currentBlock.getWorld(),
                RAND.nextBoolean() ? currentBlock.getBlockX() + x : currentBlock.getBlockX() - x,
                RAND.nextBoolean() ? currentBlock.getBlockY() + 1 : currentBlock.getBlockY(),
                RAND.nextBoolean() ? currentBlock.getBlockZ() + z : currentBlock.getBlockZ() - z);

        if(Area.getInstance().hasArea()){
            int sofa = Settings.getInstance().getStackoverflowAttempts();
            while (sofa > 0 && !Area.getInstance().isInArea(loc)){
                loc = new Location(currentBlock.getWorld(),
                        RAND.nextBoolean() ? currentBlock.getBlockX() + x : currentBlock.getBlockX() - x,
                        RAND.nextBoolean() ? currentBlock.getBlockY() + 1 : currentBlock.getBlockY(),
                        RAND.nextBoolean() ? currentBlock.getBlockZ() + z : currentBlock.getBlockZ() - z);
                sofa--;
            }

            if(sofa <= 0){
                stop();
                player.sendMessage(JumpAndRun.getInstance().getPrefix()+"§cCouldn´t found any Location. Game stopped!");
                return null;
            }
        }
        return loc;
    }

    private int[] modifyCords(int x, int z){
        if(x <= 1 && z<3){
            z += 2;
        }
        if(z <= 1 && x < 3){
            x += 2;
        }

        if(x >= 4 && z>= 4){
            x -= 1;
            z -= 1;
        }

        if(x < 3 && z < 3){
            if(RAND.nextBoolean())
                x += 2;
            else
                z += 2;
        }
        return new int[]{x,z};
    }

    private void setClayBlock(Location location){
        location.getBlock().setTypeIdAndData(Material.STAINED_CLAY.getId(), color.getData(), true);
    }
    private void setWoolBlock(Location location){
        location.getBlock().setTypeIdAndData(Material.WOOL.getId(), color.getData(), true);
    }

    private void sendActionbar(Player player, String message){
        PacketPlayOutChat packet = new PacketPlayOutChat(
                IChatBaseComponent.ChatSerializer.a("{\"text\": \""+message+"\"}"),(byte)2);
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packet);
    }

}
