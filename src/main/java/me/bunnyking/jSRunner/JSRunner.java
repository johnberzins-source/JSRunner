package me.bunnyking.jSRunner;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public class JSRunner extends JavaPlugin {

    private ScriptManager scriptManager;

    @Override
    public void onEnable() {
        // 1. Initialize ScriptManager (it registers its own listeners)
        this.scriptManager = new ScriptManager(this);

        // 2. Register the command using modern Lifecycle Events
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            // "js" is the command label.
            // You can also pass a list of aliases (e.g., List.of("javascript", "runjs")) as a 3rd argument.
            event.registrar().register("js", new JSCommandExecutor(this.scriptManager));
        });

        getLogger().info("JSRunner has been enabled successfully on Paper 1.21.10");
    }

    @Override
    public void onDisable() {
        // 3. Cleanup: Stop all scripts when the plugin is disabled to prevent memory leaks
        if (scriptManager != null) {
            scriptManager.stopAll();
        }
        getLogger().info("JSRunner has been disabled.");
    }
}
