package me.bunnyking.jSRunner;


import org.bukkit.entity.Player;
import org.graalvm.polyglot.HostAccess;

public class PlayerCommandWrapper {

    private final Player player;
    private String command;
    private boolean cancelled = false;

    public PlayerCommandWrapper(Player player, String command) {
        this.player = player;
        this.command = command;
    }

    @HostAccess.Export
    public String getPlayerName() {
        return player.getName();
    }

    @HostAccess.Export
    public String getCommand() {
        return command;
    }

    @HostAccess.Export
    public void setCommand(String command) {
        this.command = command;
    }

    @HostAccess.Export
    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
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
