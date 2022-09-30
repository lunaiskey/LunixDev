package me.lunaiskey.lunixdev.lunixrecipes;

import me.lunaiskey.lunixdev.utils.NBTUtil;
import org.bukkit.inventory.ItemStack;

public class LunixRecipeChoice {
    public static final LunixRecipeChoice AIR = new LunixRecipeChoice("AIR",0);
    private String itemID;
    private int amount;

    public LunixRecipeChoice(String itemID, int amount) {
        this.itemID = itemID.toUpperCase();
        this.amount = amount;
    }

    public boolean test(ItemStack item) {
        return NBTUtil.getLunixID(item).equals(itemID) && item.getAmount() >= amount;
    }

    public String getItemID() {
        return itemID;
    }

    public int getAmount() {
        return amount;
    }
}
