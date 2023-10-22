package de.bellobodo.smpv1.manager.flagManager;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.manager.playerManager.PlayerRole;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class FlagManager {

    private final SMPV1 smpv1;

    private FileConfiguration config;

    private BukkitTask runnable;

    private final ArrayList<PotionEffect> clanFlagHolderEffects;

    private final ArrayList<PotionEffect> clanMemberEffects;

    private final ArrayList<PotionEffect> söldnerEffects;


    public FlagManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;
        this.config = smpv1.getConfig();

        //Creation of ClanFlagHolderEffects
        ArrayList<PotionEffect> tempClanFlagHolderEffects  = new ArrayList<>();

        {
            if (config.getInt("clanFlagHolderEffects.speed") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.SPEED, 10, config.getInt("clanFlagHolderEffects.speed"), false, false));

            if (config.getInt("clanFlagHolderEffects.haste") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.FAST_DIGGING, 10, config.getInt("clanFlagHolderEffects.haste"), false, false));

            if (config.getInt("clanFlagHolderEffects.strength") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10, config.getInt("clanFlagHolderEffects.strength"), false, false));

            if (config.getInt("clanFlagHolderEffects.jump_boost") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.JUMP, 10, config.getInt("clanFlagHolderEffects.jump_boost"), false, false));

            if (config.getInt("clanFlagHolderEffects.resistance") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, config.getInt("clanFlagHolderEffects.resistance"), false, false));

            if (config.getInt("clanFlagHolderEffects.health_boost") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.HEALTH_BOOST, 10, config.getInt("clanFlagHolderEffects.health_boost"), false, false));

            if (config.getInt("clanFlagHolderEffects.luck") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.LUCK, 10, config.getInt("clanFlagHolderEffects.luck"), false, false));

            if (config.getInt("clanFlagHolderEffects.dolphins_grace") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 10, config.getInt("clanFlagHolderEffects.dolphins_grace"), false, false));

            if (config.getInt("clanFlagHolderEffects.hero_of_the_village") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 10, config.getInt("clanFlagHolderEffects.hero_of_the_village"), false, false));

            if (config.getInt("clanFlagHolderEffects.slowness") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.SLOW, 10, config.getInt("clanFlagHolderEffects.slowness"), false, false));

            if (config.getInt("clanFlagHolderEffects.weakness") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.WEAKNESS, 10, config.getInt("clanFlagHolderEffects.weakness"), false, false));

            if (config.getInt("clanFlagHolderEffects.bad_luck") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.UNLUCK, 10, config.getInt("clanFlagHolderEffects.bad_luck"), false, false));

            tempClanFlagHolderEffects.add(new PotionEffect(PotionEffectType.GLOWING, 10, 0, false, false));
        }

        this.clanFlagHolderEffects = tempClanFlagHolderEffects;

        //Creation of ClanMemberEffects
        ArrayList<PotionEffect> tempClanMemberEffects = new ArrayList<>();

        {
            if (config.getInt("clanMemberEffects.speed") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.SPEED, 10, config.getInt("clanMemberEffects.speed"), false, false));

            if (config.getInt("clanMemberEffects.haste") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.FAST_DIGGING, 10, config.getInt("clanMemberEffects.haste"), false, false));

            if (config.getInt("clanMemberEffects.strength") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10, config.getInt("clanMemberEffects.strength"), false, false));

            if (config.getInt("clanMemberEffects.jump_boost") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.JUMP, 10, config.getInt("clanMemberEffects.jump_boost"), false, false));

            if (config.getInt("clanMemberEffects.resistance") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, config.getInt("clanMemberEffects.resistance"), false, false));

            if (config.getInt("clanMemberEffects.health_boost") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.HEALTH_BOOST, 10, config.getInt("clanMemberEffects.health_boost"), false, false));

            if (config.getInt("clanMemberEffects.luck") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.LUCK, 10, config.getInt("clanMemberEffects.luck"), false, false));

            if (config.getInt("clanMemberEffects.dolphins_grace") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 10, config.getInt("clanMemberEffects.dolphins_grace"), false, false));

            if (config.getInt("clanMemberEffects.hero_of_the_village") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 10, config.getInt("clanMemberEffects.hero_of_the_village"), false, false));

            if (config.getInt("clanMemberEffects.slowness") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.SLOW, 10, config.getInt("clanMemberEffects.slowness"), false, false));

            if (config.getInt("clanMemberEffects.weakness") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.WEAKNESS, 10, config.getInt("clanMemberEffects.weakness"), false, false));

            if (config.getInt("clanMemberEffects.bad_luck") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.UNLUCK, 10, config.getInt("clanMemberEffects.bad_luck"), false, false));
        }

        this.clanMemberEffects = tempClanMemberEffects;

        //Creation of SöldnerEffects
        ArrayList<PotionEffect> tempSöldnerEffects = new ArrayList<>();

        {
            if (config.getInt("söldnerEffects.speed") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.SPEED, 10, config.getInt("söldnerEffects.speed"), false, false));

            if (config.getInt("söldnerEffects.haste") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.FAST_DIGGING, 10, config.getInt("söldnerEffects.haste"), false, false));

            if (config.getInt("söldnerEffects.strength") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10, config.getInt("söldnerEffects.strength"), false, false));

            if (config.getInt("söldnerEffects.jump_boost") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.JUMP, 10, config.getInt("söldnerEffects.jump_boost"), false, false));

            if (config.getInt("söldnerEffects.resistance") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, config.getInt("söldnerEffects.resistance"), false, false));

            if (config.getInt("söldnerEffects.health_boost") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.HEALTH_BOOST, 10, config.getInt("söldnerEffects.health_boost"), false, false));

            if (config.getInt("söldnerEffects.luck") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.LUCK, 10, config.getInt("söldnerEffects.luck"), false, false));

            if (config.getInt("söldnerEffects.dolphins_grace") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 10, config.getInt("söldnerEffects.dolphins_grace"), false, false));

            if (config.getInt("söldnerEffects.hero_of_the_village") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 10, config.getInt("söldnerEffects.hero_of_the_village"), false, false));

            if (config.getInt("söldnerEffects.slowness") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.SLOW, 10, config.getInt("söldnerEffects.slowness"), false, false));

            if (config.getInt("söldnerEffects.weakness") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.WEAKNESS, 10, config.getInt("söldnerEffects.weakness"), false, false));

            if (config.getInt("söldnerEffects.bad_luck") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.UNLUCK, 10, config.getInt("söldnerEffects.bad_luck"), false, false));

            tempSöldnerEffects.add(new PotionEffect(PotionEffectType.GLOWING, 10, 0, false, false));
        }

        this.söldnerEffects = tempSöldnerEffects;

        //Creation of BukkitRunnable
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                giveEffects();
            }
        }.runTaskTimer(smpv1, 0, 180);

        //Get active FlagHolder
        //TODO
    }

    private FlagState flagState;

    private PlayerRole flagHolderPlayerRole;

    private UUID flagHolder;

    private HashSet<UUID> clanMembers = new HashSet<>();

    //TODO Methoden hinzufügen

    public void giveEffects() {
        if (flagState != FlagState.HOLDED) return;

        if (flagHolderPlayerRole == PlayerRole.CLAN) {
            if (Bukkit.getOfflinePlayer(flagHolder).isOnline()) {

                Player player =  Bukkit.getPlayer(flagHolder);
                for (PotionEffect potionEffect:clanFlagHolderEffects) {
                    player.addPotionEffect(potionEffect);
                }

                HashSet<UUID> hashSet = smpv1.getPlayerManager().getPlayersInClan(flagHolder);
                hashSet.remove(flagHolder);
                hashSet.removeIf(uuid -> !Bukkit.getOfflinePlayer(uuid).isOnline());

                for (UUID uuid:hashSet) {
                    player = Bukkit.getPlayer(uuid);

                    for (PotionEffect potionEffect:clanMemberEffects) {
                        player.addPotionEffect(potionEffect);
                    }
                }

            }
        } else if (flagHolderPlayerRole == PlayerRole.SÖLDNER) {
            if (Bukkit.getOfflinePlayer(flagHolder).isOnline()) {
                Player player = Bukkit.getPlayer(flagHolder);

                for (PotionEffect potionEffect:söldnerEffects) {
                    player.addPotionEffect(potionEffect);
                }
            }
        }
    }
}
