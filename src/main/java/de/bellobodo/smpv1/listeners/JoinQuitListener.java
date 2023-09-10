package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.manager.flagManager.FlagManager;
import de.bellobodo.smpv1.manager.playerManager.PlayerManager;
import de.bellobodo.smpv1.manager.playerManager.PlayerRole;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinQuitListener implements Listener {

    private final SMPV1 smpv1;

    public JoinQuitListener(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (smpv1.getFlagManager().isFlagHolder(event.getPlayer().getUniqueId())) smpv1.getFlagManager().giveEffects();

        UUID playerUUID = event.getPlayer().getUniqueId();
        PlayerManager playerManager = smpv1.getPlayerManager();

        switch (playerManager.getPlayerRole(playerUUID)) {
            case CLAN:
                Bukkit.getPlayer(playerUUID).setDisplayName(ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "["
                        + ChatColor.DARK_BLUE + playerManager.getClanOfPlayer(playerUUID) + ChatColor.DARK_GRAY + "]"
                        + ChatColor.GRAY + event.getPlayer().getName());
                break;
            case SÖLDNER:
                Bukkit.getPlayer(playerUUID).setDisplayName(ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "["
                        + ChatColor.DARK_GREEN + "SÖLDNER" + ChatColor.GRAY + "]"
                        + ChatColor.GRAY + event.getPlayer().getName());
                break;
            case SPECTATOR:
                Bukkit.getPlayer(playerUUID).setDisplayName(ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "["
                        + ChatColor.WHITE + "SPECTATOR" + ChatColor.GRAY + "]"
                        + ChatColor.GRAY + event.getPlayer().getName());
                break;
        }

        event.setJoinMessage(event.getPlayer().getDisplayName() + ChatColor.RESET
                + ChatColor.GREEN + " ist dem Server beigetreten.");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(event.getPlayer().getDisplayName() + ChatColor.RESET
                + ChatColor.RED + " hat den Server verlassen.");
    }
}
