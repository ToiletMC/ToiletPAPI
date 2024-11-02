package net.toiletmc.toiletpapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToiletPAPI extends PlaceholderExpansion {
    SparkHelper sparkHelper = new SparkHelper(this);

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        int underscoreIndex = params.indexOf('_');
        String beforeUnderscore = params.substring(0, underscoreIndex);
        String afterUnderscore = params.substring(underscoreIndex + 1);

        return switch (beforeUnderscore) {
            case "emoji" -> handleEmoji(player, afterUnderscore);
            case "info" -> handleInfo(player, afterUnderscore);
            case "condition" -> handleCondition(player, afterUnderscore);
            default -> null;
        };

    }

    private String handleEmoji(Player player, String params) {
        switch (params) {
            case "server_status" -> {
                return EmojiUtil.forServerStatus(sparkHelper);
            }
            case "world" -> {
                return EmojiUtil.forWorld(player);
            }
            case "worldtime" -> {
                return EmojiUtil.forWorldtime(player);
            }
        }
        return null;
    }

    private String handleInfo(Player player, String params) {
        if (params.equals("worldtime")) {
            return EmojiUtil.forWorldtime(player) + " &7" + OtherUtil.ticksToTime(player.getWorld().getTime()) + "&r";
        }

        return null;
    }

    private String handleCondition(Player player, String params) {
        return ConditionUtil.canUseDataCard(player);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "toilet";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TheLittle_Yang";
    }

    @Override
    public @NotNull String getVersion() {
        return "${version}";
    }
}