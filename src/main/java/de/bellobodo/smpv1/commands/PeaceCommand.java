package de.bellobodo.smpv1.commands;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PeaceCommand implements CommandExecutor {

    final private SMPV1 smpv1;

    public PeaceCommand(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("smpv1.command.peace")) {
            sender.sendMessage(smpv1.getPrefix() + ChatColor.RED + "Du hast dafür keine Rechte.");
            return true;
        }

        boolean peaceful = smpv1.getGameManager().gameIsPeaceful();

        smpv1.getGameManager().setGameIsPeaceful(!peaceful);
        Bukkit.getOnlinePlayers().forEach(players -> {
            if (peaceful) players.sendMessage(smpv1.getPrefix() + ChatColor.DARK_AQUA + "Das schlagen anderer Spieler und Entities wurde "
                    + ChatColor.GREEN + "aktiviert" + ChatColor.DARK_AQUA + ".");
            else players.sendMessage(smpv1.getPrefix() + ChatColor.DARK_AQUA + "Das schlagen anderer Spieler und Entities wurde "
            + ChatColor.RED + "deaktiviert" + ChatColor.DARK_AQUA + ".");
        });
        return true;
    }
}
