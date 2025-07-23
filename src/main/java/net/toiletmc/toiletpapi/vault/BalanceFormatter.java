package net.toiletmc.toiletpapi.vault;

import java.util.List;

public class BalanceFormatter {
    private final List<BalanceSuffix> suffixes;

    public BalanceFormatter(List<BalanceSuffix> suffixes) {
        this.suffixes = suffixes;
    }

    public String formatBalance(double balance) {
        // 按照从大到小的顺序进行遍历（大单位优先）
        for (int i = suffixes.size() - 1; i >= 0; i--) {
            BalanceSuffix suffix = suffixes.get(i);
            if (balance >= suffix.threshold()) {
                double formattedBalance = balance / suffix.threshold();
                // 截断到1位小数（乘以10，使用Math.floor，除以10）
                formattedBalance = Math.floor(formattedBalance * 10) / 10;
                return formattedBalance + suffix.suffixName();
            }
        }

        // 如果没有符合条件的，默认返回截断后的1位小数格式
        balance = Math.floor(balance * 10) / 10;
        return String.valueOf(balance);
    }
}

