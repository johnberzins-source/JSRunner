package me.bunnyking.jSRunner;


import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.graalvm.polyglot.HostAccess;

public class BlockPlaceWrapper {

    private final Block block;
    private final Player player;
    private boolean cancelled = false;

    public BlockPlaceWrapper(Block block, Player player) {
        this.block = block;
        this.player = player;
    }

    @HostAccess.Export
    public String getType() {
        return block.getType().name();
    }

    @HostAccess.Export
    public int getX() {
        return block.getX();
    }

    @HostAccess.Export
    public int getY() {
        return block.getY();
    }

    @HostAccess.Export
    public int getZ() {
        return block.getZ();
    }

    @HostAccess.Export
    public String getPlayerName() {
        return player.getName();
    }

    @HostAccess.Export
    public String getPlayerUUID() {
        return player.getUniqueId().toString();
    }

    @HostAccess.Export
    public void cancel() {
        this.cancelled = true;
    }

    /* Internal */
    public boolean isCancelled() {
        return cancelled;
    }
}
