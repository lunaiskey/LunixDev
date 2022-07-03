package me.lunaiskey.lunixdev.lunixrecipes;

import com.google.common.base.Preconditions;
import me.lunaiskey.lunixdev.lunixitems.LunixItemType;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LunixShapedRecipe implements LunixRecipe {
    private ShapedRecipe ref0;

    private net.minecraft.world.item.crafting.ShapedRecipe ref2;

    private ItemStack result;

    private String[] rows;

    private Map<Character, LunixRecipeChoice> ingredients = new HashMap<>();

    public LunixShapedRecipe(ItemStack result) {
        Preconditions.checkArgument(result.getType() != Material.AIR);
        this.result = new ItemStack(result);
    }

    public boolean matches(List<ItemStack> inputItemList) {
        int match = 0;
        for (int i = 1;i<10;i++) {
            LunixRecipeChoice choice = getRecipeChoice(i);
            ItemStack slotItem = inputItemList.get(i-1);
            if (choice.test(slotItem)) {
                match++;
            } else {
                break;
            }
        }
        return match == 9;
    }

    public LunixShapedRecipe shape(String... rows) {
        this.rows = new String[3];
        for (int i = 0;i< rows.length;i++) {
            this.rows[i] = rows[i];
        }
        return this;
    }
    public LunixShapedRecipe setIngredient(char key,LunixItemType type, int amount) {
        ingredients.put(key,new LunixRecipeChoice(type,amount));
        return this;
    }
    @Override
    public ItemStack getResult() {
        return result;
    }

    public String[] getRows() {
        return rows;
    }

    public LunixRecipeChoice getRecipeChoice(int slot) {
        if (slot < 0 || slot > 9) {
            return null;
        }
        int row = ((slot - (slot-1) % 3)/3);
        String rowStr = rows[row];
        char place = rowStr.toCharArray()[(slot+2)%3];
        return ingredients.get(place);
    }

    public Map<Character, LunixRecipeChoice> getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        StringBuilder start = new StringBuilder("result=" + NBTUtil.getLunixID(result).name() + "{");
        for(int i = 1;i<10;i++) {
            start.append(getRecipeChoice(i).getType().name());
            if (i != 9) {
                start.append(",");
            } else {
                start.append("}");
            }

        }
        return start.toString();
    }
}
