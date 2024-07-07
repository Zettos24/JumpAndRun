package de.zettos.jumpandrun;

import de.zettos.jumpandrun.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {

    public Listeners() {
        Bukkit.getPluginManager().registerEvents(this, JumpAndRun.getInstance());
    }

    @EventHandler
    public void onInteractPlate(PlayerInteractEvent event){
        try {
            if(event.getClickedBlock().getType().equals(Settings.getInstance().getBlock())
            && event.getAction().equals(Action.PHYSICAL)){
                if(event.getClickedBlock().getWorld().getBlockAt(event.getClickedBlock().getLocation().subtract(0,Settings.getInstance().getUndergroundHight(), 0))
                        .getType().equals(Settings.getInstance().getUndergroundBlock())){
                    Player player = event.getPlayer();
                    if(!JumpAndRun.getInstance().isPlaying(player)){
                        JumpCore instance = new JumpCore(player);
                        instance.start();
                        JumpAndRun.getInstance().getGameMap().put(player.getUniqueId(), instance);
                    }
                }
            }
        }catch (Exception e){

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        if(JumpAndRun.getInstance().isPlaying(event.getPlayer())){
            JumpAndRun.getInstance().getGameMap().get(event.getPlayer().getUniqueId()).running();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if(JumpAndRun.getInstance().isPlaying(event.getPlayer())){
            JumpAndRun.getInstance().getGameMap().get(event.getPlayer().getUniqueId()).stop();
        }
    }


}
