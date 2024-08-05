package dev.ab.modutilslite.listener;

import dev.ab.modutilslite.ModUtilsLite;
import dev.ab.modutilslite.util.Chat;
import dev.ab.modutilslite.util.Perms;
import dev.ab.modutilslite.util.RankColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatChannelPrefixListener implements Listener {
    final private ModUtilsLite main = ModUtilsLite.getMain();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String message = e.getMessage();
        if((message.startsWith(main.getConfig().getString("settings.staff-chat-prefix"))) && (Perms.has(player, "modutils.command.staffchat"))){
            message = message.substring(message.indexOf(main.getConfig().getString("settings.staff-chat-prefix")) + 1);
            String messageToSend = main.getConfig().getString("messages.staff-chat").replaceAll("<message>", message).replaceAll("<server>", main.getConfig().getString("settings.server-name"));
            if (main.isUsingLuckPerms()){
                messageToSend = messageToSend.replaceAll("<player>", RankColor.getColoredName(player));
            } else {
                messageToSend = messageToSend.replaceAll("<player>", RankColor.getColoredName(player));
            }

            if (main.isUsingRedis()) {
                main.getRedisManager().publish("staffchat", messageToSend);
            } else {
                for (Player p: Bukkit.getOnlinePlayers()){
                    if (Perms.has(p, "modutils.command.staffchat")){
                        p.sendMessage(Chat.format(messageToSend));
                    }
                }
            }
        } else if ((message.startsWith(main.getConfig().getString("settings.admin-chat-prefix"))) && (Perms.has(player, "modutils.command.adminchat"))){
            message = message.substring(message.indexOf(main.getConfig().getString("settings.admin-chat-prefix")) + 1);
            String messageToSend = main.getConfig().getString("messages.admin-chat").replaceAll("<message>", message).replaceAll("<server>", main.getConfig().getString("settings.server-name"));
            if (main.isUsingLuckPerms()){
                messageToSend = messageToSend.replaceAll("<player>", RankColor.getColoredName(player));
            } else {
                messageToSend = messageToSend.replaceAll("<player>", RankColor.getColoredName(player));
            }

            if (main.isUsingRedis()) {
                main.getRedisManager().publish("staffchat", messageToSend);
            } else {
                for (Player p: Bukkit.getOnlinePlayers()){
                    if (Perms.has(p, "modutils.command.staffchat")){
                        p.sendMessage(Chat.format(messageToSend));
                    }
                }
            }
        }
    }
}
