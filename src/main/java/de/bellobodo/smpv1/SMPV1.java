package de.bellobodo.smpv1;

import de.bellobodo.smpv1.commands.ClanCommand;
import de.bellobodo.smpv1.commands.GameCommand;
import de.bellobodo.smpv1.commands.SpectatorCommand;
import de.bellobodo.smpv1.commands.SöldnerCommand;
import de.bellobodo.smpv1.listeners.JoinQuitListener;
import de.bellobodo.smpv1.listeners.PlayerKillListener;
import de.bellobodo.smpv1.manager.flagManager.FlagManager;
import de.bellobodo.smpv1.manager.gameManager.GameManager;
import de.bellobodo.smpv1.manager.playerManager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMPV1 extends JavaPlugin {

    private PlayerManager playerManager;

    private FlagManager flagManager;

    private GameManager gameManager;

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.playerManager = new PlayerManager(this);
        this.gameManager = new GameManager(this);
        this.flagManager = new FlagManager(this);

        getCommand("clan").setExecutor(new ClanCommand(this));
        getCommand("spectator").setExecutor(new SpectatorCommand(this));
        getCommand("söldner").setExecutor(new SöldnerCommand(this));
        getCommand("game").setExecutor(new GameCommand(this));


        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerKillListener(this), this);
    }

    @Override
    public void onDisable() {
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

    public GameManager getGameManager() {
        return gameManager;
    }
}