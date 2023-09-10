package de.bellobodo.smpv1.manager.flagManager;

import de.bellobodo.itemBuilder.ItemBuilder;
import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.manager.playerManager.PlayerManager;
import de.bellobodo.smpv1.manager.playerManager.PlayerRole;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class FlagManager {

    private final SMPV1 smpv1;

    private final ItemStack flag;

    private final ArrayList<PotionEffect> clanFlagHolderEffects;

    private final ArrayList<PotionEffect> clanMemberEffects;

    private final ArrayList<PotionEffect> söldnerEffects;

    public FlagManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;

        FileConfiguration config = smpv1.getConfig();

        //Creation of the Flag
        ItemStack banner = new ItemBuilder(Material.BLACK_BANNER)
                .setName(ChatColor.GOLD + ChatColor.BOLD.toString() + "Weltenflagge")
                .appendLore(ChatColor.YELLOW + "Eine Magische Flagge die dem Träger und")
                .appendLore(ChatColor.YELLOW + "dessen Team besondere Effekte verleit.")
                .enchant(Enchantment.LUCK)
                .hideFlags()
                .build();

        BannerMeta bannerMeta = (BannerMeta) banner.getItemMeta();

        bannerMeta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.FLOWER));
        bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.HALF_HORIZONTAL));
        bannerMeta.addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_CENTER));
        bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_RIGHT));
        bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.SQUARE_BOTTOM_LEFT));
        bannerMeta.addPattern(new Pattern(DyeColor.YELLOW, PatternType.TRIANGLES_BOTTOM));
        bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));
        bannerMeta.addPattern(new Pattern(DyeColor.BLACK, PatternType.CURLY_BORDER));

        banner.setItemMeta(bannerMeta);

        this.flag = banner;

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

            if (config.getInt("clanFlagHolderEffects.glowing") > 0) tempClanFlagHolderEffects.add
                    (new PotionEffect(PotionEffectType.GLOWING, 10, config.getInt("clanFlagHolderEffects.glowing"), false, false));
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

            if (config.getInt("clanMemberEffects.glowing") > 0) tempClanMemberEffects.add
                    (new PotionEffect(PotionEffectType.GLOWING, 10, config.getInt("clanMemberEffects.glowing"), false, false));
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

            if (config.getInt("söldnerEffects.glowing") > 0) tempSöldnerEffects.add
                    (new PotionEffect(PotionEffectType.GLOWING, 10, config.getInt("söldnerEffects.glowing"), false, false));
        }

        this.söldnerEffects = tempSöldnerEffects;

        //Creation of BukkitRunnable
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                giveEffects();
            }
        }.runTaskTimer(smpv1, 0, 180);

        //Get the FlagHolder and FlagState
        UUID holder = UUID.fromString(config.getString("flagholder"));

         if (holder != null ){
            setFlagHolder(holder);
        } else {
             flagState = FlagState.NOT_GIVEN;
         }
    }

    private FlagState flagState;

    private Item droppedFlag;

    private int timeSinceDropped = 0;

    private PlayerRole flagHolderPlayerRole;

    private UUID flagHolder;

    private HashSet<UUID> clanMembers;

    private BukkitTask runnable;

    //Remove Flag
    public boolean removeFlag() {
        switch (flagState) {
            case NOT_GIVEN: return true;
            case HOLDED:
                if (!Bukkit.getOfflinePlayer(flagHolder).isOnline()) return false;
                Bukkit.getPlayer(flagHolder).getInventory().remove(flag);
                break;
            case DROPPED:
                droppedFlag.remove();
                break;
        }

        this.flagState = FlagState.NOT_GIVEN;
        clearDroppedFlag();
        clearFlagHolder();
        return true;
    }

    //FlagState Methods
    public FlagState getFlagState() {
        return flagState;
    }

    //DroppedFlag Methods
    public void setDroppedFlag(Item item) {
        item.setGlowing(true);
        this.droppedFlag = item;
        this.flagState = FlagState.DROPPED;

        clearFlagHolder();
    }

    public Item getDroppedFlag() {
        return droppedFlag;
    }
    //TimeSinceDropped Methods
    public void countTimeSinceDropped() {
        timeSinceDropped++;
        if (timeSinceDropped == 200) {
            droppedFlag.remove();
        }
    }

    //FlagHolderPlayerRole Methods
    public PlayerRole getFlagHolderPlayerRole() {
        return flagHolderPlayerRole;
    }

    //FlagHolder Methods
    public void setFlagHolder(UUID uuid) {
        this.flagHolder = uuid;
        updateFlagHolderPlayerRole();
        clearDroppedFlag();
        this.flagState = FlagState.HOLDED;

        if (flagHolderPlayerRole == PlayerRole.CLAN) {
            HashSet<UUID> hashSet = smpv1.getPlayerManager().getPlayersInClan(uuid);
            hashSet.remove(uuid);
            this.clanMembers = hashSet;
        } else {
            this.clanMembers.clear();
        }
    }

    public boolean isFlagHolder(UUID uuid) {
        return this.flagHolder == uuid;
    }

    public UUID getFlagHolder() {
        return flagHolder;
    }

    //Other Functions
    public boolean itemIsFlag(ItemStack item) {
        return flag == item;
    }

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
                for (UUID uuid:hashSet) {
                    if (!Bukkit.getOfflinePlayer(uuid).isOnline()) hashSet.remove(uuid);
                }

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

    //Private Functions
    private void updateFlagHolderPlayerRole() {
        this.flagHolderPlayerRole = smpv1.getPlayerManager().getPlayerRole(flagHolder);
    }

    public void clearFlagHolder() {
        this.flagHolder = null;
        this.flagHolderPlayerRole = PlayerRole.UNSPECIFIED;
        this.clanMembers.clear();
    }

    public void clearDroppedFlag() {
        this.droppedFlag = null;
        this.timeSinceDropped = 0;
    }
}
