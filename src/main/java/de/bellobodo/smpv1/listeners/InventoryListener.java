package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

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
    public void onClick(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || event.getAction() == InventoryAction.PLACE_ALL
                || event.getAction() == InventoryAction.PLACE_SOME || event.getAction() == InventoryAction.PLACE_ONE ||
                event.getAction() == InventoryAction.HOTBAR_SWAP || event.getAction() == InventoryAction.SWAP_WITH_CURSOR || event.getAction() == InventoryAction.SWAP_WITH_CURSOR)) {
            List<ItemStack> items = new ArrayList<>();
            items.add(event.getCurrentItem());
            items.add(event.getCursor());
            items.add((event.getClick() == org.bukkit.event.inventory.ClickType.NUMBER_KEY) ? event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) : event.getCurrentItem());
            for (ItemStack item : items) {
                if (smpv1.getFlagManager().isFlag(item)) {
                    event.setCancelled(true);
                }
            }
        }
    }



    @EventHandler
    public void onInventoryPickupItem(InventoryPickupItemEvent event) {
        //TODO
        if (smpv1.getFlagManager().isFlag(event.getItem().getItemStack())) event.setCancelled(true);


    }




}
