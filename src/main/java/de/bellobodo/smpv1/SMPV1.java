package de.bellobodo.smpv1;

import de.bellobodo.smpv1.listeners.JoinQuitListener;
import de.bellobodo.smpv1.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMPV1 extends JavaPlugin {

    private GameManager gameManager;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        Bukkit.setWhitelist(true);

        gameManager = new GameManager(this);
    }

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "SMP V1.0" + ChatColor.GRAY + "] ";
    }
}
