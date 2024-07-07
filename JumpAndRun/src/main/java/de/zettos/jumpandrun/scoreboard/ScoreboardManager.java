package de.zettos.jumpandrun.scoreboard;

import de.zettos.jumpandrun.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private static ScoreboardManager instance;

    private final Map<Player, Scoreboard> scoreboardMap;

    private final int endHeight;

    public ScoreboardManager() {
        instance = this;
        this.scoreboardMap = new HashMap<>();
        this.endHeight = Settings.getInstance().getEndHight();
    }

    public static ScoreboardManager getInstance() {
        return instance;
    }

    public Scoreboard createBoard(Player player){
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective o = sb.registerNewObjective("lobbysystem","dummy");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName("§f§lLobbySystem");

        Team round = sb.registerNewTeam("round");
        round.setPrefix("§7");
        round.setSuffix("§c1");
        round.addEntry("§c");

        Team jumps = sb.registerNewTeam("jumps");
        jumps.setPrefix("§7");
        jumps.setSuffix("§b0");
        jumps.addEntry("§b");

        Team remain_height = sb.registerNewTeam("remain_height");
        remain_height.setPrefix("§7");
        remain_height.setSuffix("§e"+(endHeight-player.getLocation().getBlockY()+1));
        remain_height.addEntry("§e");

        Team stats = sb.registerNewTeam("stats");
        stats.setPrefix("§7");
        stats.setSuffix("§e/");
        stats.addEntry("§9");

        o.getScore("§r§r").setScore(11);
        o.getScore("§fRound").setScore(10);
        o.getScore("§c").setScore(9);

        o.getScore("§r§r§r").setScore(8);
        o.getScore("§fJumps").setScore(7);
        o.getScore("§b").setScore(6);

        o.getScore("§r§r§r§r").setScore(5);
        o.getScore("§fRemain-Height").setScore(4);
        o.getScore("§e").setScore(3);

        o.getScore("§r§r§r§r§r").setScore(2);
        o.getScore("§fStats").setScore(1);
        o.getScore("§9/").setScore(0);

        player.setScoreboard(sb);
        scoreboardMap.put(player,sb);
        return sb;
    }

    public void updateBoard(Player player, int currentRound, int currentJumps){

        Scoreboard sb;
        if(scoreboardMap.containsKey(player)){
            sb = scoreboardMap.get(player);
        }else{
            sb = createBoard(player);
        }


        Team round = sb.getTeam("round");
        round.setSuffix("§c"+ currentRound);

        Team jumps = sb.getTeam("jumps");
        jumps.setSuffix("§b" +currentJumps);

        Team remain_height = sb.getTeam("remain_height");
        remain_height.setSuffix("§e"+(endHeight-player.getLocation().getBlockY()+1));

       }

       public void removeScoreboard(Player player){
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
       }

}
