package de.bellobodo.smpv1.counter;

import de.bellobodo.timer.Counter;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class GameCounter extends Counter {
    public GameCounter(JavaPlugin instance) {
        super(instance);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void run() {


        Bukkit.getOnlinePlayers().forEach(player -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.s));
        });
    }
}
