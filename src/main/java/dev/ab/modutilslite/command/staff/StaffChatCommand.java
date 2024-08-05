package dev.ab.modutilslite.command.staff;

import dev.ab.modutilslite.ModUtilsLite;
import dev.ab.modutilslite.util.Chat;
import dev.ab.modutilslite.util.Perms;
import dev.ab.modutilslite.util.RankColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    final private ModUtilsLite main = ModUtilsLite.getMain();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            main.getLogger().info("For player use only!");
            return false;
        }

        Player player = (Player) sender;
        if (!(Perms.has(player, "modutils.command.staffchat"))){
            player.sendMessage(Chat.format("&cYou do not have permission to use this command."));
            return false;
        }

        if (args.length == 0){
            player.sendMessage(Chat.format("&eCommand usage: /sc &f<message>"));
            return false;
        }

        String message = main.getConfig().getString("messages.staff-chat");
        message = message.replaceAll("<server>", main.getConfig().getString("settings.server-name"));
        if (main.isUsingLuckPerms()){
            message = message.replaceAll("<player>", RankColor.getColoredName(player));
        } else {
            message = message.replaceAll("<player>", player.getName());
        }
        message = message.replaceAll("<message>", String.join(" ", args));

        if (main.isUsingRedis()){
            main.getRedisManager().publish("staffchat", message);
        } else {
            for (Player p : Bukkit.getOnlinePlayers()){
                if (Perms.has(p, "modutils.command.staffchat")){
                    p.sendMessage(Chat.format(message));
                }
            }
        }
        return false;
    }
}
