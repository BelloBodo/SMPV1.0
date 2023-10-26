package de.bellobodo.smpv1.commands;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.counters.GameCountdown;
import de.bellobodo.smpv1.manager.gameManager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class GameCommand implements CommandExecutor {

    private final SMPV1 smpv1;

    public GameCommand(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendUsage(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                GameManager gameManager = smpv1.getGameManager();
                if (gameManager.gameIsActive()) sender.sendMessage(smpv1.getPrefix()
                        + ChatColor.RED + "Das Spiel wurde bereits gestartet");
                else {
                    int countdown = 10;

                    GameCountdown gameCountdown = new GameCountdown(smpv1);
                    gameCountdown.startCountdown(10);
                }
                break;
            case "setflagholder": //TODO Testen
                if (args.length == 1) {
                    sendUsage(sender);
                    break;
                }
                smpv1.getFlagManager().setFlagHolder(Bukkit.getOfflinePlayer(args[1]).getUniqueId());
                sender.sendMessage(smpv1.getPrefix() + " " + ChatColor.GRAY + args[1]
                        + ChatColor.GREEN + "ist nun der Flaggenträger.");
                break;
            default:
                sendUsage(sender);
        }

        return true;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(smpv1.getPrefix() + ChatColor.BLUE + "Verwende: "
                + ChatColor.GRAY + "/game (start/setflagholder) <Spielername>" );
    }
}
