package me.bunnyking.jSRunner;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.graalvm.polyglot.*;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class ScriptManager {

    private final JavaPlugin plugin;
    private final File scriptDir;

    private final Map<String, Context> contexts = new HashMap<>();
    private final Map<String, EventAPI> eventApis = new HashMap<>();
    private final Map<String, TimerAPI> timerApis = new HashMap<>();

    public ScriptManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.scriptDir = new File(plugin.getDataFolder(), "scripts");
        if (!scriptDir.exists()) scriptDir.mkdirs();

        Bukkit.getPluginManager().registerEvents(new BukkitListener(this), plugin);
    }

    public void runScript(String name, CommandSender sender) {
        try {
            if (contexts.containsKey(name)) {
                sender.sendMessage("§cScript already running.");
                return;
            }

            File file = new File(scriptDir, name + ".js");
            if (!file.exists()) {
                sender.sendMessage("§cScript not found.");
                return;
            }

            Context context = Context.newBuilder("js")
                    .allowHostAccess(HostAccess.EXPLICIT)
                    .allowIO(false)
                    .allowCreateThread(false)
                    .allowNativeAccess(false)
                    .build();

            ScriptAPI server = new ScriptAPI(plugin);
            EventAPI events = new EventAPI();
            TimerAPI timers = new TimerAPI(plugin);

            context.getBindings("js").putMember("server", server);
            context.getBindings("js").putMember("events", events);
            context.getBindings("js").putMember("timers", timers);

            context.eval("js", Files.readString(file.toPath()));

            contexts.put(name, context);
            eventApis.put(name, events);
            timerApis.put(name, timers);

            sender.sendMessage("§aScript started: " + name);

        } catch (Exception e) {
            sender.sendMessage("§cScript error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stopScript(String name, CommandSender sender) {
        Context ctx = contexts.remove(name);
        EventAPI events = eventApis.remove(name);
        TimerAPI timers = timerApis.remove(name);

        if (ctx != null) {
            if (events != null) events.clear();
            if (timers != null) timers.clearAll();
            ctx.close();
            sender.sendMessage("§aScript stopped: " + name);
        } else {
            sender.sendMessage("§cScript not running: " + name);
        }
    }

    public void fireEvent(String event, Object arg) {
        for (EventAPI api : eventApis.values()) {
            api.fire(event, arg);
        }
    }

    public void stopAll() {
        timerApis.values().forEach(TimerAPI::clearAll);
        contexts.values().forEach(Context::close);
        timerApis.clear();
        eventApis.clear();
        contexts.clear();
    }
}
