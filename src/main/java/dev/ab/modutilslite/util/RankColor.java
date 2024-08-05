package dev.ab.modutilslite.util;

import dev.ab.modutilslite.ModUtilsLite;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RankColor {
    static LuckPerms lp = ModUtilsLite.getMain().getApi();
    static User user;

    public static String getColoredName(Player p){
        if (Bukkit.getOnlinePlayers().contains(p)) {
            user = lp.getPlayerAdapter(Player.class).getUser(p);
        } else {
            Bukkit.getScheduler().runTaskAsynchronously(ModUtilsLite.getPlugin(ModUtilsLite.class), () -> {
                user = lp.getUserManager().loadUser(p.getUniqueId()).join();
            });
        }

        Group userGroup = lp.getGroupManager().getGroup(user.getPrimaryGroup());
        if (userGroup.getCachedData().getMetaData().getMetaValue("rank-color") == null){
            return "&r" + p.getName();
        } else {
            return userGroup.getCachedData().getMetaData().getMetaValue("rank-color") + p.getName();
        }


    }
}
