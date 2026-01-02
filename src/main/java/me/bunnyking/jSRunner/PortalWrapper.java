package me.bunnyking.jSRunner;

import org.bukkit.event.EventHandler;
import org.graalvm.polyglot.HostAccess;
import org.bukkit.entity.Player;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

public class PortalWrapper {

    private final Player player;
    public PortalWrapper(Player player) {this.player = player;}

    @HostAccess.Export
    public String getName() {
        return player.getName();
    }

    @HostAccess.Export
    public String getUUID() {
        return player.getUniqueId().toString();
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

}
