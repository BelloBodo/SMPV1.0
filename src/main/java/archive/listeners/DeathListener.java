package archive.listeners;

import de.bellobodo.smpv1.SMPV1;
import archive.manager.flagManager.FlagManager;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final SMPV1 smpv1;

    public DeathListener(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        FlagManager flagManager = smpv1.getFlagManager();
        if (flagManager.getFlagHolder().equals(event.getEntity().getUniqueId())) {
            event.getDrops().remove(flagManager.getFlag());

            Location dropLocation = findSuitableLocation(event.getEntity().getLocation());
            Item item = dropLocation.getWorld().dropItem(dropLocation, smpv1.getFlagManager().getFlag());
            flagManager.setDroppedFlag(item);
        }
    }

    private Location findSuitableLocation(Location location) {
        location.setY(location.getBlockY() + 3);

        if (location.getY() < 4.5) {
            location.setY(4.5);
        }

        while (!location.getBlock().getType().isAir() && location.getBlockY() < 320){
            location.add(0, 1, 0);
        }
        return location;
    }
}
