package net.toiletmc.toiletpapi;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.type.Flags;
import me.angeschossen.lands.api.land.LandWorld;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ConditionUtil {
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    public static String canUseDataCard(Player player) {
        if (player.isOp()) {
            return TRUE;
        }

        // 检查 WorldGuard 权限
        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()));
        if (regionManager != null) {
            ApplicableRegionSet regions = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(player.getLocation()));

            if (!regions.getRegions().isEmpty()) {
                boolean hasPermission = regions.getRegions().stream().anyMatch(region ->
                        region.getMembers().contains(player.getUniqueId()) ||
                                region.getOwners().contains(player.getUniqueId()));
                if (hasPermission)
                    return TRUE;
                else
                    return FALSE;
            }

        }

        // 检查 Lands 权限
        LandsIntegration landsApi = LandsIntegration.of(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("ToiletCore")));
        LandWorld world = landsApi.getWorld(player.getWorld());
        if (world == null) return TRUE;
        boolean hasRoleFlag = world.hasRoleFlag(landsApi.getLandPlayer(player.getUniqueId()), player.getLocation(), Flags.BLOCK_PLACE, null, false);
        if (hasRoleFlag) return TRUE;

        return FALSE;
    }
}
