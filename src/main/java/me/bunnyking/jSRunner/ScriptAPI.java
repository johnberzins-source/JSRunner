package me.bunnyking.jSRunner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.graalvm.polyglot.HostAccess;

public class ScriptAPI {

    private final JavaPlugin plugin;

    public ScriptAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @HostAccess.Export
    public void log(String msg) {
        plugin.getLogger().info("[JS] " + msg);
    }

    @HostAccess.Export
    public void command(String cmd) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
    }
}
