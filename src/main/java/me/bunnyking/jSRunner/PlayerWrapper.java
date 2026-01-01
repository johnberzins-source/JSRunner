package me.bunnyking.jSRunner;

import org.graalvm.polyglot.HostAccess;
import org.bukkit.entity.Player;

public class PlayerWrapper {

    private final Player player;

    public PlayerWrapper(Player player) {
        this.player = player;
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
