package me.bunnyking.jSRunner;

import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.util.*;

public class EventAPI {

    private final Map<String, List<Value>> handlers = new HashMap<>();

    @HostAccess.Export
    public void on(String event, Value callback) {
        handlers.computeIfAbsent(event, k -> new ArrayList<>()).add(callback);
    }

    public void fire(String event, Object arg) {
        List<Value> list = handlers.get(event);
        if (list == null) return;

        for (Value fn : list) {
            try {
                fn.execute(arg);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public void clear() {
        handlers.clear();
    }
}
