package de.bellobodo.smpv1.commands;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.InputStream;
import java.net.URL;

public class ClanCommand implements CommandExecutor {

    private final SMPV1 smpv1;

    public ClanCommand(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("smpv1.command.clan")) {
            sender.sendMessage(smpv1.getPrefix() + ChatColor.RED + "Du hast dafür keine Rechte.");
            return true;
        }

        if (args.length < 2) {
            sendUsage(sender);
            return true;
        }

        String clanName = args[1].toUpperCase();

        switch (args[0].toLowerCase()) {
            case "create":
                if (clanName.equals("SÖLDNER")) {
                    sender.sendMessage(smpv1.getPrefix() + ChatColor.RED + "Der Clan " + ChatColor.GRAY + args[1]
                            + ChatColor.RED + " konnte nicht erstellt werden.");
                    break;
                }


                if (smpv1.getPlayerManager().createClan(clanName))
                    sender.sendMessage(smpv1.getPrefix() + ChatColor.GREEN + "Der Clan " + ChatColor.GRAY + args[1]
                            + ChatColor.GREEN + " wurde erstellt.");
                else sender.sendMessage(smpv1.getPrefix() + ChatColor.RED + "Der Clan " + ChatColor.GRAY + args[1]
                        + ChatColor.RED + " konnte nicht erstellt werden.");
                break;
            case "delete":
                if (smpv1.getPlayerManager().deleteClan(clanName))
                    sender.sendMessage(smpv1.getPrefix() + ChatColor.GREEN + "Der Clan " + ChatColor.GRAY + args[1]
                            + ChatColor.GREEN + " wurde gelöscht.");
                else sender.sendMessage(smpv1.getPrefix() + ChatColor.RED + "Der Clan " + ChatColor.GRAY + args[1]
                        + ChatColor.RED + " konnte nicht gelöscht werden.");
                break;
            case "addmember":
                if (args.length != 3) {
                    sendUsage(sender);
                    return true;
                }

                if (smpv1.getPlayerManager().addClanMember(clanName, Bukkit.getOfflinePlayer(args[2]).getUniqueId()))
                    sender.sendMessage(smpv1.getPrefix() + ChatColor.GREEN + "Der Spieler " + ChatColor.GRAY + args[2]
                            + ChatColor.GREEN + " wurde zum Clan hinzugefügt.");
                else sender.sendMessage(smpv1.getPrefix() + ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[2]
                        + ChatColor.RED + " konnte nicht zum Clan hinzugefügt werden.");
                break;
            case "removemember":
                if (args.length != 3) {
                    sendUsage(sender);
                    return true;
                }

                if (smpv1.getPlayerManager().removeClanMember(clanName, Bukkit.getOfflinePlayer(args[2]).getUniqueId()))
                    sender.sendMessage(smpv1.getPrefix() + ChatColor.GREEN + "Der Spieler " + ChatColor.GRAY + args[2]
                            + ChatColor.GREEN + " wurde vom Clan entfernt.");
                else sender.sendMessage(smpv1.getPrefix() + ChatColor.RED + "Der Spieler " + ChatColor.GRAY + args[2]
                        + ChatColor.RED + " konnte nicht vom Clan entfernt werden.");
                break;
            default:
                sendUsage(sender);
        }
        return true;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(smpv1.getPrefix() + ChatColor.BLUE + "Verwende: " + ChatColor.GRAY + "/clan (create/delete/addMember/removeMember) <ClanName> [Spielername]" );
    }
}
