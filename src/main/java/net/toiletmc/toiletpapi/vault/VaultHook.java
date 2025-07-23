package net.toiletmc.toiletpapi.vault;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class VaultHook {
    private final Economy economy;
    private BalanceFormatter balanceFormatter;

    public VaultHook(PlaceholderExpansion expansion, Economy economy) {
        this.economy = economy;
        loadBalanceSuffixes(expansion.getStringList("vault.short-numbers-suffixes"));
    }

    private void loadBalanceSuffixes(List<String> suffixList) {
        List<BalanceSuffix> suffixes = new ArrayList<>();

        for (String suffix : suffixList) {
            String[] parts = suffix.split("-");
            if (parts.length == 2) {
                String suffixName = parts[0];
                double threshold = Double.parseDouble(parts[1]);
                suffixes.add(new BalanceSuffix(suffixName, threshold));
            }
        }

        balanceFormatter = new BalanceFormatter(suffixes);
    }

    public String getBalance(Player player) {
        double balance = economy.getBalance(player);
        return balanceFormatter.formatBalance(balance);
    }
}
