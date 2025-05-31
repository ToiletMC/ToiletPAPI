package net.toiletmc.toiletpapi;

import org.bukkit.World;
import org.bukkit.entity.Player;

public class EmojiUtil {
    public static String forServerStatus(SparkHelper sparkHelper) {
        double tpsLast10Secs = sparkHelper.getLast10SecsTPS();
        if (tpsLast10Secs >= 18) {
            double msptLast10Secs = sparkHelper.getLast10SecsMSPT();
            if (msptLast10Secs <= 80) {
                return "&a\uD83C\uDF11";
            } else {
                return "&2\uD83C\uDF11";
            }
        } else if (tpsLast10Secs >= 15) {
            return "&6\uD83C\uDF11";
        } else if (tpsLast10Secs >= 10) {
            return "&c\uD83C\uDF11";
        } else if (tpsLast10Secs >= 6) {
            return "&4\uD83C\uDF11";
        } else {
            return "`&4\uD83D\uDC80";
        }
    }

    public static String forWorld(Player player) {
        if (player.getWorld().getName().startsWith("resource_")) {
            return "&7⛏&r";
        } else {
            return "";
        }
    }

    public static String forWorldtime(Player player) {
        World world = player.getWorld();

        // 下界和末地没有昼夜的概念。
        if (world.getEnvironment() == World.Environment.NETHER ||
                world.getEnvironment() == World.Environment.THE_END) {
            return "&e\uD83C\uDF1A&r";
        }

        long gameTime = world.getTime() % 24000;
        if (gameTime >= 0 && gameTime <= 12000) {
            return "&6☀&r";
        } else {
            return "&e\uD83C\uDF1A&r";
        }
    }
}
