package net.toiletmc.toiletpapi;

import com.google.common.collect.ImmutableMap;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.milkbowl.vault.economy.Economy;
import net.toiletmc.toiletpapi.vault.VaultHook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class ToiletPAPI extends PlaceholderExpansion implements Configurable {
    SparkHelper sparkHelper = new SparkHelper(this);
    VaultHook vaultHook = null;

    public ToiletPAPI() {
        super();
        setupEconomy();
    }

    private void setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }

        vaultHook = new VaultHook(this, rsp.getProvider());
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        int underscoreIndex = params.indexOf('_');

        String beforeUnderscore;
        String afterUnderscore;

        if (underscoreIndex != -1) {
            beforeUnderscore = params.substring(0, underscoreIndex);
            afterUnderscore = params.substring(underscoreIndex + 1);
        } else {
            beforeUnderscore = params;
            afterUnderscore = "";
        }


        return switch (beforeUnderscore) {
            case "emoji" -> handleEmoji(player, afterUnderscore);
            case "info" -> handleInfo(player, afterUnderscore);
            case "condition" -> handleCondition(player, afterUnderscore);
            case "vault" -> handleVault(player, afterUnderscore);
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

    private String handleVault(Player player, String params) {
        if (vaultHook == null) {
            return "Vault not found";
        } else {
            return vaultHook.getBalance(player);
        }
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

    @Override
    public Map<String, Object> getDefaults() {
        return ImmutableMap.<String, Object>builder()
                .put("vault.short-numbers-suffixes", List.of("万-10000", "亿-100000000", "兆-1000000000000"))
                .build();
    }
}