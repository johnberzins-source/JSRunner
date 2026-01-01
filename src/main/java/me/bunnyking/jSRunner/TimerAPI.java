package me.bunnyking.jSRunner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TimerAPI {

    private final JavaPlugin plugin;
    private final AtomicInteger ids = new AtomicInteger();
    private final Map<Integer, Integer> tasks = new HashMap<>();

    public TimerAPI(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @HostAccess.Export
    public int setTimeout(Value fn, int ticks) {
        int id = ids.incrementAndGet();
        int task = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            try { fn.execute(); } catch (Throwable t) { t.printStackTrace(); }
            tasks.remove(id);
        }, ticks).getTaskId();
        tasks.put(id, task);
        return id;
    }

    @HostAccess.Export
    public int setInterval(Value fn, int ticks) {
        int id = ids.incrementAndGet();
        int task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            try { fn.execute(); } catch (Throwable t) { t.printStackTrace(); }
        }, ticks, ticks).getTaskId();
        tasks.put(id, task);
        return id;
    }

    @HostAccess.Export
    public void clear(int id) {
        Integer task = tasks.remove(id);
        if (task != null) Bukkit.getScheduler().cancelTask(task);
    }

    public void clearAll() {
        tasks.values().forEach(Bukkit.getScheduler()::cancelTask);
        tasks.clear();
    }
}
