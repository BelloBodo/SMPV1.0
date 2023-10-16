package de.bellobodo.smpv1.commands;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class FlagCommand implements CommandExecutor {

    private final SMPV1 smpv1;

    public FlagCommand(SMPV1 smpv1) {
        this.smpv1 = smpv1;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        return true;
    }

    private void sendUsage(CommandSender sender) {

    }
}
