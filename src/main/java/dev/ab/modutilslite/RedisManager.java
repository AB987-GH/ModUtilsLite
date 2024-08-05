package dev.ab.modutilslite;

import dev.ab.modutilslite.util.Chat;
import dev.ab.modutilslite.util.Perms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class RedisManager {
    private final JedisPool jedisPool;
    private final JedisPubSub jedisPubSub;

    public RedisManager(String host, String password, int port) {
        this.jedisPool = new JedisPool(new JedisPoolConfig(), host, port, 2000, password);
        this.jedisPubSub = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                handleRedisMessage(channel, message);
            }
        };
    }

    public void subscribe(String... channels){
        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.subscribe(jedisPubSub, channels);
            }
        }).start();
    }

    public void publish(String channel, String message){
        try (Jedis jedis = jedisPool.getResource()){
            jedis.publish(channel, message);
        }
    }

    public void close(){
        jedisPool.close();
    }

    private void handleRedisMessage(String channel, String message){
        switch (channel){
            case "staffchat":
                for (Player p: Bukkit.getOnlinePlayers()){
                    if (Perms.has(p, "modutils.command.staffchat")){
                        p.sendMessage(Chat.format(message));
                    }
                }
                break;
            case "adminchat":
                for (Player p: Bukkit.getOnlinePlayers()){
                    if (Perms.has(p, "modutils.command.adminchat")){
                        p.sendMessage(Chat.format(message));
                    }
                }
                break;
            case "reports":
                for (Player p : Bukkit.getOnlinePlayers()){
                    if (Perms.has(p, "modutils.reports.view")){
                        p.sendMessage(Chat.format(message));
                    }
                }
                break;
        }

    }
}
