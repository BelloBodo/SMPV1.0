package de.bellobodo.smpv1.manager;

import de.bellobodo.itemBuilder.ItemBuilder;
import de.bellobodo.smpv1.SMPV1;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

public class FlagManager {

    private final SMPV1 smpv1;

    private final ItemStack flag;

    public FlagManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;

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

        flag = banner;
    }






}
