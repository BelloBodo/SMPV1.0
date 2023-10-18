package de.bellobodo.smpv1.commands;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SöldnerCommand implements CommandExecutor {

    private final SMPV1 smpv1;

    public SöldnerCommand(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("smpv1.command.söldner")) {
            sender.sendMessage(smpv1.getPrefix() + ChatColor.RED + "Du hast dafür keine Rechte.");
            return true;
        }

        if (args.length < 2) {
            sendUsage(sender);
            return true;
        }

        String playerName = args[1];

        switch (args[0].toLowerCase()) {
            case "add":
                smpv1.getPlayerManager().addSöldner(Bukkit.getOfflinePlayer(playerName).getUniqueId());
                sender.sendMessage(smpv1.getPrefix() + ChatColor.GREEN + "Der Söldner " + ChatColor.GRAY + args[1]
                        + ChatColor.GREEN + " wurde erstellt.");
                break;
            case "remove":
                smpv1.getPlayerManager().addSöldner(Bukkit.getOfflinePlayer(playerName).getUniqueId());
                sender.sendMessage(smpv1.getPrefix() + ChatColor.GREEN + "Der Söldner " + ChatColor.GRAY + args[1]
                        + ChatColor.GREEN + " wurde entfernt.");
                break;
            default:
                sendUsage(sender);
        }
        return true;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(smpv1.getPrefix() + ChatColor.BLUE + "Verwende: " + ChatColor.GRAY + "/söldner (add/remove) <Spielername>" );
    }
}
