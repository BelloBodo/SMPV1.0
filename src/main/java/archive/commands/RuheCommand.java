package archive.commands;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RuheCommand implements CommandExecutor {

    final private FileConfiguration cH;

    final private ItemStack[] clearedItems = {
            new ItemStack(Material.END_CRYSTAL),
            new ItemStack(Material.RESPAWN_ANCHOR)
    };

    final private PotionEffectType[] givenEffects = {
            PotionEffectType.JUMP,
            PotionEffectType.WEAKNESS,
            PotionEffectType.SATURATION
    };

    public RuheCommand (SMPV1 ch) {
        this.cH = ch.getConfig();
    }



    /**
     *      *   TODO: Maybe kann man das anders schreiben: anstatt in der Config zu schreiben, kann man den Ruhemenschen ne Rolle geben und nen Handerler/Listener der der Rolle immer den Effekt gibt
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("OPERATOR")) {
            sender.sendMessage("§4Du hast dafür keine Rechte lol");
            return true;
        }

        Player rentner = sender.getServer().getPlayer(args[0]); //Arg 1 - angeklagter

        if(args.length>1) { //check ob mehr als 1 Arg -> reset
            if(args[1].toLowerCase().equals("reset")) {

                rentner.sendMessage("§aDu bist nicht mehr im Ruhestand");
                sender.sendMessage("§a"+rentner.getName()+" ist nun nicht mehr im Ruhestand");

                rentner.setGameMode(GameMode.SURVIVAL);
                rentner.setWalkSpeed(0.2f);

                for (PotionEffectType pet: givenEffects) {
                    rentner.removePotionEffect(pet);
                }

            } else {
                sender.sendMessage("§4Ein Fehler ist aufgetreten. Meintest du §c /ruhe <player> reset §4?");
            }
            return true;
        }


        //kein 2nd Arg, wir wollen jmd zu Ruhe setzen

        rentner.sendMessage("§4Du wurdest in den Ruhestand gesetzt");
        sender.sendMessage("§4"+rentner.getName()+" ist nun im Ruhestand");

        rentner.setGameMode(GameMode.ADVENTURE);
        rentner.setWalkSpeed(0);

        for (PotionEffectType pet: givenEffects) {
            rentner.addPotionEffect(new PotionEffect(pet,-1,128,false,false,false));
        }
        for (ItemStack item: clearedItems) {
            rentner.getInventory().remove(item);
        }

        return true;

    }
}
