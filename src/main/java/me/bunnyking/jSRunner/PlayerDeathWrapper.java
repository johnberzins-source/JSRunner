package me.bunnyking.jSRunner;



import org.bukkit.entity.Player;
import org.graalvm.polyglot.HostAccess;

public class PlayerDeathWrapper {

    private final Player player;
    private final String deathMessage;
    private final String killerName;

    public PlayerDeathWrapper(Player player, String deathMessage, String killerName) {
        this.player = player;
        this.deathMessage = deathMessage;
        this.killerName = killerName;
    }

    @HostAccess.Export
    public String getPlayerName() {
        return player.getName();
    }

    @HostAccess.Export
    public String getUUID() {
        return player.getUniqueId().toString();
    }

    @HostAccess.Export
    public String getDeathMessage() {
        return deathMessage;
    }

    @HostAccess.Export
    public String getKillerName() {
        return killerName;
    }
}
