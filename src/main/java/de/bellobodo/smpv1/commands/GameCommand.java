package de.bellobodo.smpv1.commands;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.manager.gameManager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GameCommand implements CommandExecutor {

    private final SMPV1 smpv1;

    public GameCommand(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sendUsage(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                GameManager gameManager = smpv1.getGameManager();
                if (gameManager.gameIsActive()) sender.sendMessage(smpv1.getPrefix()
                        + ChatColor.RED + "Das Spiel wurde bereits gestartet");
                else {
                    gameManager.setGameIsActive(true);
                    sender.sendMessage(smpv1.getPrefix()
                            + ChatColor.GREEN + "Das Spiel wurde gestartet");
                    Bukkit.getOnlinePlayers().forEach(players -> {
                        players.sendTitle(ChatColor.GOLD + "Spiel", ChatColor.YELLOW + "wurde gestartet.", 20, 3 * 20, 20);
                        players.playSound(players.getLocation(), Sound.ITEM_GOAT_HORN_SOUND_0, 1, 1);
                    });
                }
                break;
            case "setborder":

                break;
            default:
                sendUsage(sender);
        }

        return true;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(smpv1.getPrefix() + ChatColor.BLUE + "Verwende: "
                + ChatColor.GRAY + "/game (start/setborder) <Borderradius>" );
    }
}
