package net.toiletmc.toiletpapi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import me.lucko.spark.api.statistic.types.GenericStatistic;

public class SparkHelper {
    private Spark spark;

    public SparkHelper(PlaceholderExpansion expansion) {
        try {
            spark = SparkProvider.get();
            expansion.info("已挂钩到 Spark");
        } catch (Exception e) {
            expansion.severe("spark 服务异常，已停用插件");
            expansion.unregister();
        }
    }

    public double getLast5SecsTPS() {
        if (spark == null) {
            return 0;
        }

        DoubleStatistic<StatisticWindow.TicksPerSecond> tps = spark.tps();
        return tps.poll(StatisticWindow.TicksPerSecond.SECONDS_5);
    }

    public double getLast10SecsMSPT() {
        if (spark == null) {
            return 0;
        }

        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> msptInfo = spark.mspt();
        return msptInfo.poll(StatisticWindow.MillisPerTick.SECONDS_10).percentile95th();
    }

    public double getLast1MinMSPT() {
        if (spark == null) {
            return 0;
        }

        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> msptInfo = spark.mspt();
        return msptInfo.poll(StatisticWindow.MillisPerTick.MINUTES_1).percentile95th();
    }
}
