package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.manager.playerManager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        Player player = Bukkit.getPlayer(playerUUID);
        String playerName = "";


        switch (playerManager.getPlayerRole(playerUUID)) {
            case CLAN:
                playerName = ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "["
                        + ChatColor.DARK_BLUE + playerManager.getClanOfPlayer(playerUUID) + ChatColor.DARK_GRAY + "] "
                        + ChatColor.GRAY + event.getPlayer().getName();
                player.setDisplayName(playerName);
                player.setPlayerListName(playerName);
                break;
            case SÖLDNER:
                playerName = ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "["
                        + ChatColor.DARK_GREEN + "SÖLDNER" + ChatColor.DARK_GRAY + "] "
                        + ChatColor.GRAY + event.getPlayer().getName();
                player.setDisplayName(playerName);
                player.setPlayerListName(playerName);
                break;
            case SPECTATOR:
                playerName = ChatColor.BOLD + ChatColor.DARK_GRAY.toString() + "["
                        + ChatColor.WHITE + "SPECTATOR" + ChatColor.DARK_GRAY + "] "
                        + ChatColor.GRAY + event.getPlayer().getName();
                player.setDisplayName(playerName);
                player.setPlayerListName(playerName);
                break;
        }

        event.setJoinMessage(event.getPlayer().getDisplayName() + ChatColor.RESET
                + ChatColor.GREEN + " ist dem Server beigetreten.");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        //TODO entferne Effekte bei FlagHolder
        event.setQuitMessage(event.getPlayer().getDisplayName() + ChatColor.RESET
                + ChatColor.RED + " hat den Server verlassen.");
    }

}
