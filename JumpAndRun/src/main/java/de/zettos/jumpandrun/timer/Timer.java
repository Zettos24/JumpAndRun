package de.zettos.jumpandrun.timer;

import de.zettos.jumpandrun.JumpAndRun;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class Timer {

    private static Timer instance;

    private final Map<Player, BukkitTask> taskMap;

    public static Timer getInstance() {
        return instance;
    }

    public Timer() {
        instance = this;
        this.taskMap = new HashMap<>();
    }

    public void startTimer(Player player){

        if(taskMap.containsKey(player)){
            return;
        }

        Time time = new Time(0,0,0);

        BukkitTask bukkitTask = (new BukkitRunnable(){

            @Override
            public void run() {
                sendActionbar(player, time.updateBySecond());
            }
        }).runTaskTimerAsynchronously(JumpAndRun.getInstance(), 20L,20L);

        taskMap.put(player,bukkitTask);
    }

    public void stopTimer(Player player){
        taskMap.get(player).cancel();
        taskMap.remove(player);
    }

    private void sendActionbar(final Player player, final String msg) {
        IChatBaseComponent ibc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}").a(msg);
        PacketPlayOutChat packet = new PacketPlayOutChat(ibc, (byte) 2);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

}
