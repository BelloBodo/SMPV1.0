package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ItemPickupListener implements Listener {

    public final SMPV1 smpv1;

    public ItemPickupListener(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER && event.getItem().getItemStack() == smpv1.getFlagManager().getFlag()) {
            Player player = (Player) event.getEntity();

            smpv1.getFlagManager().setFlagHolder(player.getUniqueId());
        }
    }
}