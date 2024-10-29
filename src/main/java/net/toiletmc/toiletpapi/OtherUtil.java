package net.toiletmc.toiletpapi;

public class OtherUtil {
    public static final int ticksAtMidnight = 18000;
    public static final int ticksPerDay = 24000;
    public static final int ticksPerHour = 1000;
    public static final double ticksPerMinute = 1000d / 60d;

    public static String ticksToTime(long ticks) {
        ticks = ticks - ticksAtMidnight + ticksPerDay;
        long hours = ticks / ticksPerHour;
        ticks -= hours * ticksPerHour;
        long mins = (long) Math.floor(ticks / ticksPerMinute);
        if (hours >= 24) {
            hours = hours - 24;
        }
        return (hours < 10 ? "0" + hours : hours) + ":" + (mins < 10 ? "0" + mins : mins);
    }
}
