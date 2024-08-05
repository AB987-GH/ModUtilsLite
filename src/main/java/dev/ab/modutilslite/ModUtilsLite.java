package dev.ab.modutilslite;

import dev.ab.modutilslite.command.essential.TeleportCommand;
import dev.ab.modutilslite.command.essential.TeleportHereCommand;
import dev.ab.modutilslite.command.staff.AdminChatCommand;
import dev.ab.modutilslite.command.staff.StaffChatCommand;
import dev.ab.modutilslite.command.staff.VanishCommand;
import dev.ab.modutilslite.listener.ChatChannelPrefixListener;
import lombok.Getter;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModUtilsLite extends JavaPlugin {

    @Getter
    private boolean usingLuckPerms;

    @Getter
    private boolean usingRedis;

    @Getter
    LuckPerms api;

    @Getter
    private static ModUtilsLite main;

    @Getter
    private RedisManager redisManager;

    @Getter
    private VanishCommand vanishCommand;

    @Override
    public void onEnable() {
        main = this;

        saveDefaultConfig();

        vanishCommand = new VanishCommand();

        if ((this.getServer().getPluginManager().getPlugin("LuckPerms") != null) && (getConfig().getBoolean("settings.luckperms-rank-colors"))){
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null){
                api = provider.getProvider();
                usingLuckPerms = true;
            }
        }

        if (getConfig().getBoolean("redis.enabled")){
            getLogger().info("Connecting to Redis...");

            String host = getConfig().getString("redis.host");
            String password = getConfig().getString("redis.password");
            int port = getConfig().getInt("redis.port");

            redisManager = new RedisManager(host, password, port);

            redisManager.subscribe("staffchat", "adminchat", "reports");

            usingRedis = true;
            getLogger().info("Connected to Redis!");
        }

        // Registering commands
        getCommand("vanish").setExecutor(vanishCommand);
        getCommand("sc").setExecutor(new StaffChatCommand());
        getCommand("ac").setExecutor(new AdminChatCommand());

        if (getConfig().getBoolean("settings.essentials-enabled")){
            getCommand("tp").setExecutor(new TeleportCommand());
            getCommand("tphere").setExecutor(new TeleportHereCommand());
        }

        Bukkit.getPluginManager().registerEvents(new ChatChannelPrefixListener(), this);



    }

    @Override
    public void onDisable() {
        redisManager.close();

    }
}
