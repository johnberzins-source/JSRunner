package me.bunnyking.jSRunner;


import org.bukkit.entity.Player;
import org.graalvm.polyglot.HostAccess;

public class ChatWrapper {

    private final Player player;
    private String message;
    private boolean cancelled = false;

    public ChatWrapper(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    // ---- Player info ----

    @HostAccess.Export
    public String getName() {
        return player.getName();
    }

    @HostAccess.Export
    public String getUUID() {
        return player.getUniqueId().toString();
    }

    // ---- Message control ----

    @HostAccess.Export
    public String getMessage() {
        return message;
    }

    @HostAccess.Export
    public void setMessage(String message) {
        this.message = message;
    }

    @HostAccess.Export
    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
