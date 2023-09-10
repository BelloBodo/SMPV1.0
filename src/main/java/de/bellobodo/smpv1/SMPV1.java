package de.bellobodo.smpv1;

import de.bellobodo.smpv1.counter.GameCounter;
import de.bellobodo.smpv1.listeners.JoinQuitListener;
import de.bellobodo.smpv1.manager.flagManager.FlagManager;
import de.bellobodo.smpv1.manager.playerManager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMPV1 extends JavaPlugin {

    /* TODO
        Prio 1:
        //Hotbar Message
        //Countdown Dropped Flag
        Prio 2:
        //Commands
        Prio 3:
     */

    private PlayerManager playerManager;

    private FlagManager flagManager;

    private GameCounter gameCounter;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
        Bukkit.setWhitelist(true);

        playerManager = new PlayerManager(this);
        flagManager = new FlagManager(this);
        gameCounter = new GameCounter(this);
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

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public FlagManager getFlagManager() {
        return flagManager;
    }
}
