package me.lunaiskey.lunixdev.lunixitems;

import org.apache.commons.lang.WordUtils;

public enum LunixItemType {
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    SWORD,
    PICKAXE,
    AXE,
    SHOVEL,
    HOE,
    FISHING_ROD,
    BOW,
    ;

    public String getName() {
        return this.name().toUpperCase().replace('_',' ');
    }

    public String getNiceName() {
        return WordUtils.capitalizeFully(this.name().replace('_',' '));
    }
}
