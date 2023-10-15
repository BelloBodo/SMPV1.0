package de.bellobodo.smpv1.counter;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.manager.flagManager.FlagState;
import de.bellobodo.timer.Counter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

public class GameCounter extends Counter {

    private final SMPV1 smpv1;

    public GameCounter(JavaPlugin instance) {
        super(instance);
        smpv1 = (SMPV1) instance;
    }

    @Override
    public void onStart() {
    }

    int effectCountdown = 5;

    @Override
    public void run() {
        //Give Effects
        effectCountdown--;
        if (effectCountdown <= 0) {
            if (Objects.requireNonNull(Bukkit.getPlayer(smpv1.getFlagManager().getFlagHolder())).isOnline()) {
                smpv1.getFlagManager().giveEffects();
            }
            effectCountdown = 5;
        }

        //Countdown timeSinceDropped
        if (smpv1.getFlagManager().getFlagState() == FlagState.DROPPED) smpv1.getFlagManager().countTimeSinceDropped();

        //Display the Hotbar Message
        String hotbarMessage = getHotbarMessage();
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(hotbarMessage));
        });
    }

    private String getHotbarMessage() {
        switch (smpv1.getFlagManager().getFlagState()) {
            case NOT_GIVEN:
                return ChatColor.GRAY + "Die Flagge wurde nicht vergeben.";
            case HOLDED:
                String playerName = Bukkit.getOfflinePlayer(smpv1.getFlagManager().getFlagHolder()).getName();
                String clanName = smpv1.getPlayerManager().getClanOfPlayer(smpv1.getFlagManager().getFlagHolder());
                if (clanName != null) {
                    return ChatColor.GRAY + "Die Flagge ist im Besitz von " + ChatColor.DARK_GRAY + playerName
                            + ChatColor.GRAY + " im Clan \"" + ChatColor.DARK_GRAY + clanName + ChatColor.GRAY + "\".";
                }
                return ChatColor.GRAY + "Die Flagge ist im Besitz von " + ChatColor.DARK_GRAY + playerName + ChatColor.GRAY + ".";
            case DROPPED:
                return ChatColor.GRAY + "Die Flagge wurde fallengelassen. Sie wird in "
                        + ChatColor.DARK_GRAY + (200 - smpv1.getFlagManager().getTimeSinceDropped()) + " Sekunden"
                        + ChatColor.GRAY + " despawnen.";
        }
        return null;
    }
}
