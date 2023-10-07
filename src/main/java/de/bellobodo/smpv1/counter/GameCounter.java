package de.bellobodo.smpv1.counter;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.manager.flagManager.FlagState;
import de.bellobodo.timer.Counter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class GameCounter extends Counter {

    private final SMPV1 smpv1;

    public GameCounter(JavaPlugin instance) {
        super(instance);
        smpv1 = (SMPV1) instance;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void run() {

        //Display the Hotbar Message
        String hotbarMessage = getHotbarMessage();
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(hotbarMessage));
        });
    }

    //TODO Make Hotbar Message

    private String getHotbarMessage() {
        switch (smpv1.getFlagManager().getFlagState()) {
            case NOT_GIVEN:
                return ChatColor.GRAY + "Die Flagge wurde noch nicht vergeben.";
            case HOLDED:
                String playerName = Bukkit.getOfflinePlayer(smpv1.getFlagManager().getFlagHolder()).getName();
                String clanName = smpv1.getPlayerManager().getClanOfPlayer(smpv1.getFlagManager().getFlagHolder());

                //TODO Make HotbarMessage for handling Söldner

                return ChatColor.GRAY + "Die Flagge ist im Besitz von " + ChatColor.DARK_GRAY + playerName
                        + ChatColor.GRAY + " im Clan \"" + ChatColor.DARK_GRAY + clanName + ChatColor.GRAY + "\".";
            case DROPPED:
                return ChatColor.GRAY + "Die Flagge wurde fallengelassen.";
        }
        return null;
    }
}
