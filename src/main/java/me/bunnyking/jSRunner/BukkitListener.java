package me.bunnyking.jSRunner;

import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BukkitListener implements Listener {

    private final ScriptManager manager;

    public BukkitListener(ScriptManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        manager.fireEvent("playerJoin", new PlayerWrapper(e.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        manager.fireEvent("playerQuit", new PlayerWrapper(e.getPlayer()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        manager.fireEvent("blockBreak", new BlockBreakWrapper(e.getBlock(), e.getPlayer()));
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        PlayerCommandWrapper wrapper =
                new PlayerCommandWrapper(event.getPlayer(), event.getMessage());

        manager.fireEvent("playerCommand", wrapper);

        if (wrapper.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setMessage(wrapper.getCommand());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        String deathMessage = event.getDeathMessage();
        String killerName = null;

        if (player.getKiller() != null) {
            killerName = player.getKiller().getName();
        }

        PlayerDeathWrapper wrapper =
                new PlayerDeathWrapper(player, deathMessage, killerName);

        manager.fireEvent("playerDeath", wrapper);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        BlockPlaceWrapper wrapper =
                new BlockPlaceWrapper(event.getBlock(), event.getPlayer());

        manager.fireEvent("blockPlace", wrapper);

        if (wrapper.isCancelled()) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityDamageWrapper wrapper =
                new EntityDamageWrapper(
                        event.getEntity(),
                        event.getCause(),
                        event.getDamage()
                );

        manager.fireEvent("entityDamage", wrapper);

        if (wrapper.isCancelled()) {
            event.setCancelled(true);
        } else {
            event.setDamage(wrapper.getDamage());
        }
    }


    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        TeleportCause cause = event.getCause();

        if (cause == TeleportCause.NETHER_PORTAL || cause == TeleportCause.END_PORTAL) {
            PortalWrapper wrapper = new PortalWrapper(
                    event.getPlayer(),
                    cause.name(),
                    event.getFrom(),
                    event.getTo()
            );

            manager.fireEvent("playerEnterPortal", wrapper);

            if (wrapper.isCancelled()) {
                event.setCancelled(true);
            } else if (wrapper.getTo() != null) {
                event.setTo(wrapper.getTo());
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        ChatWrapper wrapper = new ChatWrapper(
                event.getPlayer(),
                event.getMessage()
        );

        manager.fireEvent("playerChat", wrapper);

        if (wrapper.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        event.setMessage(wrapper.getMessage());
    }





}
