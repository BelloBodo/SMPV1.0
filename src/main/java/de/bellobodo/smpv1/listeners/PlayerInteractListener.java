package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    final private SMPV1 smpv1;

    public PlayerInteractListener(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(player.isOp()) return;

        if (player.getWorld().getEnvironment().equals(World.Environment.THE_END)) return;

        try {
            if (event.getItem().equals(new ItemStack(Material.RESPAWN_ANCHOR)) || event.getItem().equals(new ItemStack(Material.END_CRYSTAL))) event.setCancelled(true);
        } catch (Exception ignored) {}

    }


}
