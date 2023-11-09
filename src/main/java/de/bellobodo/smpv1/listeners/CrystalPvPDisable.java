package de.bellobodo.smpv1.listeners;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CrystalPvPDisable implements Listener {

    final private SMPV1 smpv1;

    public CrystalPvPDisable(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onBlocPlace(PlayerInteractEvent event) {
        Player evplayer = event.getPlayer();

        if(evplayer.isOp()) return;

        if (evplayer.getWorld().getEnvironment().equals(World.Environment.THE_END)) return;

        try {
            if (event.getItem().equals(new ItemStack(Material.RESPAWN_ANCHOR)) || event.getItem().equals(new ItemStack(Material.END_CRYSTAL))) event.setCancelled(true);
        } catch (Exception e) {

        }

    }


}
