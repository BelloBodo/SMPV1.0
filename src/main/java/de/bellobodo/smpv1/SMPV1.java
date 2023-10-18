package de.bellobodo.smpv1;

import de.bellobodo.smpv1.commands.ClanCommand;
import de.bellobodo.smpv1.commands.SpectatorCommand;
import de.bellobodo.smpv1.commands.SöldnerCommand;
import de.bellobodo.smpv1.playerManager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMPV1 extends JavaPlugin {

    private PlayerManager playerManager = new PlayerManager(this);

    @Override
    public void onLoad() {
        this.saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        getCommand("clan").setExecutor(new ClanCommand(this));
        getCommand("spectator").setExecutor(new SpectatorCommand(this));
        getCommand("söldner").setExecutor(new SöldnerCommand(this));
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
}
