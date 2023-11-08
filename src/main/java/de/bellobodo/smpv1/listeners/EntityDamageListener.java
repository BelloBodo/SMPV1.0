package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class EntityDamageListener implements Listener {

    final private SMPV1 smpv1;

    public EntityDamageListener(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().isOp()) return;
        if (event.getDamager() instanceof Player player && player.getGameMode() == GameMode.CREATIVE) return;

        event.setCancelled(smpv1.getGameManager().gameIsPeaceful());
    }
}
