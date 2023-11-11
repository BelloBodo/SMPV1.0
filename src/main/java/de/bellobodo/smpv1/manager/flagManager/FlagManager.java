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

import java.util.*;

public class FlagManager {

    private final SMPV1 smpv1;

    private FileConfiguration config;

    private BukkitTask runnable;

    private final ArrayList<PotionEffect> clanFlagHolderEffects;

    private final ArrayList<PotionEffect> clanMemberEffects;

    private final ArrayList<PotionEffect> söldnerEffects;


    public FlagManager(SMPV1 smpv1) {// Das hier ist UNGLAUBLICH ineffizient... Aber ist egal weils ja nur einmal ausgeführt wird xD
        this.smpv1 = smpv1;
        this.config = smpv1.getConfig();

        Bukkit.getLogger().info("[SMPV1] Start Building FlagManager");

        //Creation of ClanFlagHolderEffects
        ArrayList<PotionEffect> tempClanFlagHolderEffects  = new ArrayList<>();

        {
            if (config.getInt("clanFlagHolderEffects.speed") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.SPEED, 10 * 20, config.getInt("clanFlagHolderEffects.speed") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.haste") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, config.getInt("clanFlagHolderEffects.haste") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.strength") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10 * 20, config.getInt("clanFlagHolderEffects.strength") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.jump_boost") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.JUMP, 10 * 20, config.getInt("clanFlagHolderEffects.jump_boost") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.resistance") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 20, config.getInt("clanFlagHolderEffects.resistance") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.health_boost") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.HEALTH_BOOST, 10 * 20, config.getInt("clanFlagHolderEffects.health_boost") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.luck") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.LUCK, 10 * 20, config.getInt("clanFlagHolderEffects.luck") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.dolphins_grace") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 10 * 20, config.getInt("clanFlagHolderEffects.dolphins_grace") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.hero_of_the_village") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 10 * 20, config.getInt("clanFlagHolderEffects.hero_of_the_village") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.slowness") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.SLOW, 10 * 20, config.getInt("clanFlagHolderEffects.slowness") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.weakness") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.WEAKNESS, 10 * 20, config.getInt("clanFlagHolderEffects.weakness") - 1, false, false));

            if (config.getInt("clanFlagHolderEffects.bad_luck") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.UNLUCK, 10 * 20, config.getInt("clanFlagHolderEffects.bad_luck") - 1, false, false));

            tempClanFlagHolderEffects.add(new PotionEffect(PotionEffectType.GLOWING, 10 * 20, 0, false, false));
        }

        this.clanFlagHolderEffects = tempClanFlagHolderEffects;

        //Creation of ClanMemberEffects
        ArrayList<PotionEffect> tempClanMemberEffects = new ArrayList<>();

        {
            if (config.getInt("clanMemberEffects.speed") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.SPEED, 10 * 20, config.getInt("clanMemberEffects.speed") - 1, false, false));

            if (config.getInt("clanMemberEffects.haste") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, config.getInt("clanMemberEffects.haste") - 1, false, false));

            if (config.getInt("clanMemberEffects.strength") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10 * 20, config.getInt("clanMemberEffects.strength") - 1, false, false));

            if (config.getInt("clanMemberEffects.jump_boost") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.JUMP, 10 * 20, config.getInt("clanMemberEffects.jump_boost") - 1, false, false));

            if (config.getInt("clanMemberEffects.resistance") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 20, config.getInt("clanMemberEffects.resistance") - 1, false, false));

            if (config.getInt("clanMemberEffects.health_boost") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.HEALTH_BOOST, 10 * 20, config.getInt("clanMemberEffects.health_boost") - 1, false, false));

            if (config.getInt("clanMemberEffects.luck") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.LUCK, 10 * 20, config.getInt("clanMemberEffects.luck") - 1, false, false));

            if (config.getInt("clanMemberEffects.dolphins_grace") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 10 * 20, config.getInt("clanMemberEffects.dolphins_grace") - 1, false, false));

            if (config.getInt("clanMemberEffects.hero_of_the_village") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 10 * 20, config.getInt("clanMemberEffects.hero_of_the_village") - 1, false, false));

            if (config.getInt("clanMemberEffects.slowness") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.SLOW, 10 * 20, config.getInt("clanMemberEffects.slowness") - 1, false, false));

            if (config.getInt("clanMemberEffects.weakness") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.WEAKNESS, 10 * 20, config.getInt("clanMemberEffects.weakness") - 1, false, false));

            if (config.getInt("clanMemberEffects.bad_luck") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.UNLUCK, 10 * 20, config.getInt("clanMemberEffects.bad_luck") - 1, false, false));
        }

        this.clanMemberEffects = tempClanMemberEffects;

        //Creation of SöldnerEffects
        ArrayList<PotionEffect> tempSöldnerEffects = new ArrayList<>();

        {
            if (config.getInt("söldnerEffects.speed") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.SPEED, 10 * 20, config.getInt("söldnerEffects.speed") - 1, false, false));

            if (config.getInt("söldnerEffects.haste") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, config.getInt("söldnerEffects.haste") - 1, false, false));

            if (config.getInt("söldnerEffects.strength") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10 * 20, config.getInt("söldnerEffects.strength") - 1, false, false));

            if (config.getInt("söldnerEffects.jump_boost") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.JUMP, 10 * 20, config.getInt("söldnerEffects.jump_boost") - 1, false, false));

            if (config.getInt("söldnerEffects.resistance") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10 * 20, config.getInt("söldnerEffects.resistance") - 1, false, false));

            if (config.getInt("söldnerEffects.health_boost") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.HEALTH_BOOST, 10 * 20, config.getInt("söldnerEffects.health_boost") - 1, false, false));

            if (config.getInt("söldnerEffects.luck") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.LUCK, 10 * 20, config.getInt("söldnerEffects.luck") - 1, false, false));

            if (config.getInt("söldnerEffects.dolphins_grace") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 10 * 20, config.getInt("söldnerEffects.dolphins_grace") - 1, false, false));

            if (config.getInt("söldnerEffects.hero_of_the_village") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 10 * 20, config.getInt("söldnerEffects.hero_of_the_village") - 1, false, false));

            if (config.getInt("söldnerEffects.slowness") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.SLOW, 10 * 20, config.getInt("söldnerEffects.slowness") - 1, false, false));

            if (config.getInt("söldnerEffects.weakness") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.WEAKNESS, 10 * 20, config.getInt("söldnerEffects.weakness") - 1, false, false));

            if (config.getInt("söldnerEffects.bad_luck") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.UNLUCK, 10 * 20, config.getInt("söldnerEffects.bad_luck") - 1, false, false));

            tempSöldnerEffects.add(new PotionEffect(PotionEffectType.GLOWING, 10 * 20, 0, false, false));
        }

        this.söldnerEffects = tempSöldnerEffects;

        //Set active FlagHolder
        String tempFlagHolderString = config.getString("flagholder");
        if (tempFlagHolderString != null) {
            UUID tempFlagHolder = UUID.fromString(tempFlagHolderString);
            if (setFlagHolder(tempFlagHolder)) {
                Bukkit.getLogger().info("Flagholder hinzugefügt: "
                        + Bukkit.getOfflinePlayer(tempFlagHolder).getName());
            }
            else Bukkit.getLogger().info("Kein valider Flagholder konnte gefunden werden.");
        }

        //Creation of BukkitRunnable
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (smpv1.getGameManager().gameIsActive()) giveEffects();
            }
        }.runTaskTimer(smpv1, 0, 180);

        Bukkit.getLogger().info("[SMPV1] End Building FlagManager");
    }

    private FlagState flagState = null;

    private PlayerRole flagHolderPlayerRole = null;

    private UUID flagHolder = null;

    private String clanName = "";

    public boolean setFlagHolder(UUID uuid) {
        PlayerRole playerRole = smpv1.getPlayerManager().getPlayerRole(uuid);

        if (playerRole == null) return false;
        if (playerRole == PlayerRole.SPECTATOR || playerRole == PlayerRole.UNSPECIFIED) return false;

        giveEffects();

        this.flagState = FlagState.HOLDED;
        this.flagHolder = uuid;
        this.flagHolderPlayerRole = playerRole;
        this.clanName = smpv1.getPlayerManager().getClanOfPlayer(uuid);
        Bukkit.getLogger().info("neuer Clan: " + smpv1.getPlayerManager().getClanOfPlayer(uuid));
        if (playerRole == PlayerRole.CLAN) smpv1.getPlayerManager().getClanOfPlayer(uuid);

        smpv1.getConfig().set("flagholder", uuid.toString());
        smpv1.saveConfig();

        return true;
    }

    public void setRandomFlagHolder(ArrayList<UUID> exceptedUUIDs) {
        ArrayList<UUID> validPlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> validPlayers.add(player.getUniqueId()));
        validPlayers.removeAll(smpv1.getPlayerManager().getSpectators());
        validPlayers.removeAll(exceptedUUIDs);

        int random;
        UUID uuid;
        if (!validPlayers.isEmpty()) {
            random = new Random().nextInt(validPlayers.size());
            uuid = validPlayers.get(random);
        } else {
            random = new Random().nextInt(exceptedUUIDs.size());
            uuid = exceptedUUIDs.get(random);
        }
        setFlagHolder(validPlayers.get(random));
    }

    public UUID getFlagHolder() {
        return flagHolder;
    }

    public boolean isFlagHolder(UUID uuid) {
        if (flagHolder == null) return false;
        return flagHolder.equals(uuid);
    }

    public void removeFlagHolder() {
        this.flagState = FlagState.NOT_GIVEN;
        this.flagHolder = null;
        this.flagHolderPlayerRole = null;
    }

    public void giveEffects() {//Umlaute in Variablennamen sind echt nich schön, achja und achtet auf euree Indendtations
        if (flagState != FlagState.HOLDED) return;

        if (flagHolderPlayerRole == PlayerRole.CLAN) {
            if (Bukkit.getOfflinePlayer(flagHolder).isOnline()) {

                Player player =  Bukkit.getPlayer(flagHolder);
                for (PotionEffect potionEffect:clanFlagHolderEffects) {
                    assert player != null;//Achja: Bidde achtet darauf, dass ihr nullpointer explizit fangt. Sonst sucht ihr euch irgendwann tot xD
                    player.addPotionEffect(potionEffect);
                }

                HashSet<UUID> hashSet = smpv1.getPlayerManager().getPlayersInClan(clanName);
                assert hashSet != null;
                hashSet.remove(flagHolder);
                hashSet.removeIf(uuid -> !Bukkit.getOfflinePlayer(uuid).isOnline());

                for (UUID uuid:hashSet) {
                    player = Bukkit.getPlayer(uuid);

                    for (PotionEffect potionEffect:clanMemberEffects) {
                        assert player != null;
                        player.addPotionEffect(potionEffect);
                    }
                }

            }
        } else if (flagHolderPlayerRole == PlayerRole.SOELDNER) {
            if (Bukkit.getOfflinePlayer(flagHolder).isOnline()) {
                Player player = Bukkit.getPlayer(flagHolder);

                for (PotionEffect potionEffect:söldnerEffects) {
                    assert player != null;
                    player.addPotionEffect(potionEffect);
                }
            }
        }
    }
}
