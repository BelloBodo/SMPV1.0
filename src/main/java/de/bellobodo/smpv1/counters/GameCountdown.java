package de.bellobodo.smpv1.counters;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.timer.Countdown;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCountdown extends Countdown {

    private final SMPV1 smpv1;

    public GameCountdown(JavaPlugin instance) {
        super(instance);
        this.smpv1 = (SMPV1) instance;
    }

    @Override
    public void onStart() {
        Bukkit.getOnlinePlayers().forEach(players ->  {
            players.sendMessage(smpv1.getPrefix() + ChatColor.GOLD + "Der Countdown wurde gestartet.");
        });
    }

    @Override
    public void onEnd() {
        Bukkit.getWorlds().forEach(worlds -> {
            WorldBorder worldBorder = worlds.getWorldBorder();
            worldBorder.setSize(700, 20);
        });
        Bukkit.getOnlinePlayers().forEach(players ->  {
            players.sendMessage(smpv1.getPrefix() + ChatColor.GREEN + "Das Spiel geht los");
            players.sendTitle(ChatColor.DARK_GREEN + "Das Spiel", ChatColor.GREEN + "geht los.", 20, 3 * 20, 20);
            players.playSound(players.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 1, 1);
        });
        smpv1.getFlagManager().giveEffects();

        Bukkit.getLogger().info("[SMPV1] Das Spiel ist nun aktiv.");
        smpv1.getGameManager().setGameIsActive(true);

    }

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(players -> {
            players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + String.valueOf(getRemainingSeconds())));
        });
    }
}
