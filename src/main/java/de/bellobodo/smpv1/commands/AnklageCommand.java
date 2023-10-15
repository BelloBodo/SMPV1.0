package de.bellobodo.smpv1.commands;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class AnklageCommand implements CommandExecutor {

    final private FileConfiguration config;

    final private ItemStack[] clearedItems = {
            new ItemStack(Material.ENDER_PEARL),
            new ItemStack(Material.WATER_BUCKET),
            new ItemStack(Material.LAVA_BUCKET),
            new ItemStack(Material.HONEY_BOTTLE),
            new ItemStack(Material.MILK_BUCKET),
            new ItemStack(Material.ARROW),
            new ItemStack(Material.END_CRYSTAL),
            new ItemStack(Material.RESPAWN_ANCHOR)
    };

    final private PotionEffectType[] givenEffects = {
            PotionEffectType.JUMP,
            PotionEffectType.WEAKNESS,
            PotionEffectType.SATURATION
    };

    public AnklageCommand (SMPV1 smpv1) {
        this.config = smpv1.getConfig();
    }

    /**
     * Wird bei Commandausführung aufgerufen
     *   TODO: Maybe kann man das anders schreiben: anstatt in der Config zu schreiben, kann man den Angeklagten ne Rolle geben und nen Handerler/Listener der der Rolle immer den Effekt gibt
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Rechte checken
        if(!sender.hasPermission("OPERATOR")) {
            sender.sendMessage("§4Du hast dafür keine Rechte lol");
            return true;
        }

        Player angeklagter = sender.getServer().getPlayer(args[0]); //Arg 1 - angeklagter


        /*
        *   /anklage reset   ->  angeklagter wird entfernt
         */
        if(args[0].equalsIgnoreCase("reset")) {

            try {
                angeklagter = sender.getServer().getPlayer(UUID.fromString((String) config.get("angeklagt")));

                config.set("angeklagt", "00000000-0000-0000-0000-000000000000");

                angeklagter.setGameMode(GameMode.SURVIVAL);
                angeklagter.setWalkSpeed(0.2f);

                for (PotionEffectType pE: givenEffects) {
                    angeklagter.removePotionEffect(pE);
                }

                angeklagter.sendMessage("§aDu bist nicht mehr angeklagt");
                sender.sendMessage("§a"+angeklagter.getName()+" ist nun nicht mehr angeklagt");
            } catch (Exception e) {
                sender.sendMessage("§2Kritischer Fehler bro. Ist überhaupt jemand angeklagt?");
            }


        } else {
            try {
                config.set("angeklagt", angeklagter.getUniqueId().toString());

                String kpStr = (String) config.get("angeklagterpos");

                double[] klagepos = {   Double.parseDouble(kpStr.substring(0,kpStr.indexOf("|"))),
                                        Double.parseDouble(kpStr.substring(kpStr.indexOf("|")+1,kpStr.indexOf("#"))),
                                        Double.parseDouble(kpStr.substring(kpStr.indexOf("#")+1))
                                    };


                angeklagter.sendMessage("§4D'bist anjeklaachd!");
                sender.sendMessage("§4"+angeklagter.getName()+" ist nun angeklagt");

                angeklagter.setGameMode(GameMode.ADVENTURE);
                angeklagter.teleport(new Location(angeklagter.getWorld(), klagepos[0],klagepos[1],klagepos[2],0,0));

                angeklagter.setWalkSpeed(0);

                for (PotionEffectType pE: givenEffects) {
                    angeklagter.addPotionEffect(new PotionEffect(pE,-1,128,false,false,false));
                }
                for (ItemStack item: clearedItems) {
                    angeklagter.getInventory().remove(item);
                }

            } catch (Exception e) {
                sender.sendMessage("§2Kritischer Fehler bro. Haste die /klagepos gesetzt?");
            }


        }

        return true;
    }
}
