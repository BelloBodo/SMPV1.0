package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    private final SMPV1 smpv1;

    public JoinQuitListener(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

    }

}
