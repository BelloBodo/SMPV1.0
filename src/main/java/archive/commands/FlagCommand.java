package archive.commands;

import de.bellobodo.smpv1.SMPV1;
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
        Player player = (Player) sender;
        player.getInventory().addItem(smpv1.getFlagManager().getFlag());
        smpv1.getFlagManager().setFlagHolder(player.getUniqueId());
        return true;
    }

    private void sendUsage(CommandSender sender) {

    }
}
