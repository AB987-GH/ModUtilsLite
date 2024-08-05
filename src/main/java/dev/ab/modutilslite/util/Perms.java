package dev.ab.modutilslite.util;

import org.bukkit.entity.Player;

public class Perms {

    public static boolean has(Player p, String perm){ return (p.hasPermission(perm)); }

}
