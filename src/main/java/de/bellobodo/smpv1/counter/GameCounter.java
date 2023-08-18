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
        FlagState flagState = smpv1.getFlagManager().getFlagState();
        String playerName;
        String clanName;

        if (flagState == FlagState.HOLDED) {
            playerName = Bukkit.getOfflinePlayer(smpv1.getFlagManager().getFlagHolder()).getName();
            clanName = smpv1.getPlayerManager().getClanOfPlayer(smpv1.getFlagManager().getFlagHolder());
        }

        //TODO Make Hotbar Message
        String hotbarMessage;


        Bukkit.getOnlinePlayers().forEach(player -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.s));
        });
    }
}
