package archive.commands;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetKlagePos implements CommandExecutor {

    final private FileConfiguration cH;

    public SetKlagePos (SMPV1 ch) {
        this.cH = ch.getConfig();
    }

    /**
     * Setzt die Position wo der Angeklagt hinteleportiert wird
     * /setklagepos             - setzt auf die aktuelle Pos des Nutzers.
     * MAYBE TODO: /setklagepos <x><y><z> ?      || Muss aber nich
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("OPERATOR")) {
            sender.sendMessage("§4Du hast dafür keine Rechte lol");
            return true;
        }

        /*
         * do not question my intellect
         */
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location playerpos = player.getLocation();
            String pos =    Math.floor(playerpos.getX()*10)/10 + "|"
                        +   Math.floor(playerpos.getY()*10)/10 + "#"
                        +   Math.floor(playerpos.getZ()*10)/10;

            cH.set("angeklagterpos", pos);

            player.sendMessage("§aAnklägerposition gespeichert!");
        }
        return true;
    }
}
