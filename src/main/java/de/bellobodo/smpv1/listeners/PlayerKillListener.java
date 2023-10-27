package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerKillListener implements Listener {

    private final SMPV1 smpv1;

    public PlayerKillListener(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) { //TODO testen
        Player player = event.getEntity();

        if (!smpv1.getFlagManager().isFlagHolder(player.getUniqueId())) return; //Only continue if Player is the FlagHolder

        if (player.isDead() && player.getKiller() != null) { //Check if player was killed by a player
            smpv1.getFlagManager().setFlagHolder(player.getKiller().getUniqueId());
            Bukkit.getOnlinePlayers().forEach(players -> {
                players.sendMessage(smpv1.getPrefix() + player.getKiller().getDisplayName() + " ist nun der Flaggenträger");
            });
        } else {
            smpv1.getFlagManager().setRandomFlagHolder();
            Bukkit.getOnlinePlayers().forEach(players -> {
                players.sendMessage(smpv1.getPrefix() + Bukkit.getPlayer(smpv1.getFlagManager().getFlagHolder()).getDisplayName() + " ist nun zufällig der neue Flaggenträger");
            });
        }

    }
}
