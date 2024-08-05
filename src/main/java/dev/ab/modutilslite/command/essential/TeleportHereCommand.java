package dev.ab.modutilslite.command.essential;

import dev.ab.modutilslite.ModUtilsLite;
import dev.ab.modutilslite.util.Chat;
import dev.ab.modutilslite.util.Perms;
import dev.ab.modutilslite.util.RankColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportHereCommand implements CommandExecutor {

    final private ModUtilsLite main = ModUtilsLite.getMain();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("For player use only!");
            return false;
        }

        Player player = (Player) sender;

        if (!(Perms.has(player, "modutils.command.tphere"))){
            player.sendMessage(Chat.format("&cYou do not have permission to use this command!"));
            return false;
        }

        if (args.length != 1){
            player.sendMessage(Chat.format("&cUsage: /tphere <player>"));
            return false;
        }

        if (!(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0])))){
            player.sendMessage(Chat.format("&cThat player was not found!"));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        String msg = main.getConfig().getString("messages.teleport-here");
        String alertMsg = main.getConfig().getString("messages.teleport-here-alert");

        if (main.isUsingLuckPerms()){
            msg = msg.replaceAll("<target>", RankColor.getColoredName(target));
            alertMsg = alertMsg.replaceAll("<player>", RankColor.getColoredName(player)).replaceAll("<target>", RankColor.getColoredName(target));
        } else {
            msg = msg.replaceAll("<target>", target.getDisplayName());
            alertMsg = alertMsg.replaceAll("<player>", player.getDisplayName()).replaceAll("<target>", target.getDisplayName());
        }

        player.teleport(target.getLocation());
        player.sendMessage(Chat.format(msg));
        for (Player p : Bukkit.getOnlinePlayers()){
            if (Perms.has(p, "modutils.alerts.teleport-here")){
                p.sendMessage(Chat.format(alertMsg));
            }
        }

        return false;
    }
}
