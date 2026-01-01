package me.bunnyking.jSRunner;

import org.graalvm.polyglot.HostAccess;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BlockBreakWrapper {

    private final Block block;
    private final Player player;

    public BlockBreakWrapper(Block block, Player player) {
        this.block = block;
        this.player = player;
    }

    @HostAccess.Export
    public String getType() {
        return block.getType().name();
    }

    @HostAccess.Export
    public int getX() { return block.getX(); }

    @HostAccess.Export
    public int getY() { return block.getY(); }

    @HostAccess.Export
    public int getZ() { return block.getZ(); }

    @HostAccess.Export
    public String getPlayerName() {
        return player.getName();
    }
}
