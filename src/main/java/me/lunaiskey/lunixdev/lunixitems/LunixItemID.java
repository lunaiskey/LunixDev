package me.lunaiskey.lunixdev.lunixitems;

import org.apache.commons.lang.WordUtils;
@Deprecated
public enum LunixItemID {
    AIR,
    PLAYER_MENU,
    COPPER_ORE,
    IRON_ORE,
    COAL_ORE,
    COAL,
    ENCHANTED_COAL,
    COAL_BLOCK,
    GLASS_PANE,
    BRONZE_HELMET,
    BRONZE_CHESTPLATE,
    BRONZE_LEGGINGS,
    BRONZE_BOOTS,
    DADS_ASHES,
    DILDO_ON_A_STICK,
    DROP_OF_CUM,
    DRAGON_CUM,
    MONTHLY_CRATE,
    IRON_INGOT,
    IRON_HELMET,
    IRON_CHESTPLATE,
    IRON_LEGGINGS,
    IRON_BOOTS,
    BACKPACK,
    DUCKY_SPINE,
    ;

    public String getBaseName() {
        return WordUtils.capitalize(this.name().toLowerCase());
    }
}
