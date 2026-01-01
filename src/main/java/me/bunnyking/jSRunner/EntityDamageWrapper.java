package me.bunnyking.jSRunner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.graalvm.polyglot.HostAccess;

public class EntityDamageWrapper {

    private final Entity entity;
    private final EntityDamageEvent.DamageCause cause;
    private double damage;
    private boolean cancelled = false;

    public EntityDamageWrapper(Entity entity, EntityDamageEvent.DamageCause cause, double damage) {
        this.entity = entity;
        this.cause = cause;
        this.damage = damage;
    }

    @HostAccess.Export
    public String getEntityType() {
        return entity.getType().name();
    }

    @HostAccess.Export
    public boolean isPlayer() {
        return entity instanceof Player;
    }

    @HostAccess.Export
    public String getPlayerName() {
        if (entity instanceof Player player) {
            return player.getName();
        }
        return null;
    }

    @HostAccess.Export
    public String getCause() {
        return cause.name();
    }

    @HostAccess.Export
    public double getDamage() {
        return damage;
    }

    @HostAccess.Export
    public void setDamage(double damage) {
        this.damage = damage;
    }

    @HostAccess.Export
    public void cancel() {
        this.cancelled = true;
    }

    /* Internal */
    public boolean isCancelled() {
        return cancelled;
    }
}
