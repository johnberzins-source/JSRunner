package me.bunnyking.jSRunner;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.graalvm.polyglot.HostAccess;

public class PortalWrapper {

    private final Player player;
    private final String portalType;
    private Location to;
    private boolean cancelled = false;

    public PortalWrapper(Player player, String portalType, Location from, Location to) {
        this.player = player;
        this.portalType = portalType;
        this.to = to;
    }

    @HostAccess.Export
    public String getName() {
        return player.getName();
    }

    @HostAccess.Export
    public String getUUID() {
        return player.getUniqueId().toString();
    }

    @HostAccess.Export
    public String getPortalType() {
        return portalType; // "NETHER_PORTAL" or "END_PORTAL"
    }

    @HostAccess.Export
    public int getX() {
        return player.getLocation().getBlockX();
    }

    @HostAccess.Export
    public int getY() {
        return player.getLocation().getBlockY();
    }

    @HostAccess.Export
    public int getZ() {
        return player.getLocation().getBlockZ();
    }

    @HostAccess.Export
    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public Location getTo() {
        return to;
    }
}
