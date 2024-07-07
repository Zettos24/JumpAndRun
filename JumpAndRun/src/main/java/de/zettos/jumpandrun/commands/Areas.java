package de.zettos.jumpandrun.commands;


import de.zettos.jumpandrun.JumpAndRun;
import de.zettos.jumpandrun.config.Area;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Areas implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage(JumpAndRun.getInstance().getPrefix()+"§cYou have to be a player to perform this command!");
            return true;
        }

        if(command.getName().equalsIgnoreCase("area")){
            if(args.length < 1){
                sender.sendMessage(Area.getInstance().hasArea() ? JumpAndRun.getInstance().getPrefix()+ "The Area is between §e"+Area.getInstance().toPositionString(
                        Area.getInstance().getPosition1(),"Pos1") + " §7and §e"+
                        Area.getInstance().toPositionString(
                                Area.getInstance().getPosition2(),"Pos2")
                        : JumpAndRun.getInstance().getPrefix()+"No area was specified in the settings! §cUse: /area <set : confirm> <1,2>");
                return true;
            }

            Player player = (Player) sender;

            if(args[0].equalsIgnoreCase("set") && args.length == 2){
                int pos = -1;
                try {
                    pos = Integer.parseInt(args[1]);
                }catch (Exception e){
                    player.sendMessage(JumpAndRun.getInstance().getPrefix() + "§cPlease enter a number between 1 and 2!");
                    return true;
                }

                if(pos < 1 || pos > 2){
                    player.sendMessage(JumpAndRun.getInstance().getPrefix() + "§cPlease enter a number between 1 and 2!");
                    return true;
                }

                if(pos == 1){
                    Area.getInstance().setPosition1(player.getLocation());
                }else Area.getInstance().setPosition2(player.getLocation());

                player.sendMessage(JumpAndRun.getInstance().getPrefix()+"You´ve §aconfirmed position " + pos + "§7. If you confirmed both positions you can save them with " +
                        "§e/area confirm§7.");
                return true;
            }else if(args[0].equalsIgnoreCase("confirm") && args.length == 1){
                if(Area.getInstance().save()){
                    player.sendMessage(JumpAndRun.getInstance().getPrefix()+"§aSuccessfully saved §7both positions!");
                }else player.sendMessage(JumpAndRun.getInstance().getPrefix()+"§cYou haven´t confirmed all positions. Therefore, the process was aborted!");
                return true;
            }else{
                player.sendMessage(JumpAndRun.getInstance().getPrefix()+"§cUse: /area <set : confirm> <1,2>");
            }

        }
        return true;
    }
}
