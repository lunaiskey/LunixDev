package me.lunaiskey.lunixdev.lunixrecipes;

import me.lunaiskey.lunixdev.lunixitems.LunixItemType;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import org.bukkit.inventory.ItemStack;

public class LunixRecipeChoice {

    private LunixItemType type;
    private int amount;

    public LunixRecipeChoice(LunixItemType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public boolean test(ItemStack item) {
        return item.getAmount() >= amount && NBTUtil.getLunixID(item).equals(type);
    }

    public LunixItemType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
