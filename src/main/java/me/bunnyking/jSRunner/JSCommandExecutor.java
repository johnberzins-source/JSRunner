package me.bunnyking.jSRunner;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.List;

public class JSCommandExecutor implements BasicCommand {

    private final ScriptManager scriptManager;

    public JSCommandExecutor(ScriptManager scriptManager) {
        this.scriptManager = scriptManager;
    }

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        CommandSender sender = stack.getSender(); // Extract sender from stack

        if (!sender.hasPermission("jsrunner.admin")) {
            sender.sendMessage("§cNo permission");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage("§e/js run <name>");
            sender.sendMessage("§e/js stop <name>");
            sender.sendMessage("§e/js reload");
            return;
        }

        // Use switch with arrow syntax (Java 17+ / Paper 1.21+)
        switch (args[0].toLowerCase()) {
            case "run" -> {
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /js run <name>");
                } else {
                    scriptManager.runScript(args[1], sender);
                }
            }
            case "stop" -> {
                if (args.length < 2) {
                    sender.sendMessage("§cUsage: /js stop <name>");
                } else {
                    scriptManager.stopScript(args[1], sender);
                }
            }
            case "reload" -> {
                scriptManager.stopAll();
                sender.sendMessage("§aAll scripts stopped.");
            }
            default -> sender.sendMessage("§cUnknown sub-command.");
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("run", "stop", "reload");
        }
        return List.of();
    }
}
