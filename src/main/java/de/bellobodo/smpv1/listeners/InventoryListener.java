package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryListener implements Listener {

    private final SMPV1 smpv1;

    public InventoryListener(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (smpv1.getFlagManager().isFlag(event.getItemDrop().getItemStack())) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent event) {
        if (smpv1.getFlagManager().isFlag(event.getItem())) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryPickupItem(InventoryPickupItemEvent event) {
        if (smpv1.getFlagManager().isFlag(event.getItem().getItemStack())) event.setCancelled(true);
    }


}
