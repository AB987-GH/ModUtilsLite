package dev.ab.modutilslite.command.staff;

import dev.ab.modutilslite.ModUtilsLite;
import dev.ab.modutilslite.util.Chat;
import dev.ab.modutilslite.util.Perms;
import dev.ab.modutilslite.util.RankColor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {

    final private ModUtilsLite main = ModUtilsLite.getMain();

    @Getter
    private ArrayList<UUID> vanished = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            main.getLogger().info("For player use only!");
            return false;
        }
        Player player = (Player) sender;
        if (!(Perms.has(player, "modutils.command.vanish"))){
            player.sendMessage(Chat.format("&cYou do not have permission to use this command!"));
            return false;
        }

        if (args.length != 0){
            player.sendMessage(Chat.format("&cUsage: /vanish"));
            return false;
        }

        if (vanished.contains(player.getUniqueId())){
            for (Player p: Bukkit.getOnlinePlayers()){
                p.showPlayer(player);
                if (Perms.has(p, "modutils.alerts.vanish")){
                    if (main.isUsingLuckPerms()){
                        p.sendMessage(Chat.format(main.getConfig().getString("messages.unvanish-alert").replaceAll("<player>", RankColor.getColoredName(player))));
                    } else {
                        p.sendMessage(Chat.format(main.getConfig().getString("messages.unvanish-alert").replaceAll("<player>", player.getName())));
                    }
                }
            }
            vanished.remove(player.getUniqueId());
            player.sendMessage(Chat.format(main.getConfig().getString("messages.unvanish")));
        } else {
            for (Player p: Bukkit.getOnlinePlayers()){
                if (Perms.has(p, "modutils.command.vanish")) {
                    p.showPlayer(player);
                }

                if (Perms.has(p, "modutils.alerts.vanish")){
                    if (main.isUsingLuckPerms()){
                        p.sendMessage(Chat.format(main.getConfig().getString("messages.vanish-alert").replaceAll("<player>", RankColor.getColoredName(player))));
                    } else {
                        p.sendMessage(Chat.format(main.getConfig().getString("messages.vanish-alert").replaceAll("<player>", player.getName())));
                    }
                }
            }
            vanished.add(player.getUniqueId());
            player.sendMessage(Chat.format(main.getConfig().getString("messages.vanish")));

        }
        return false;

    }
}