package me.lunaiskey.lunixdev.lunixrecipes;

import it.unimi.dsi.fastutil.ints.IntList;
import me.lunaiskey.lunixdev.inventories.craftingrelated.LunixCraftingInv;
import me.lunaiskey.lunixdev.lunixitems.LunixItemType;
import me.lunaiskey.lunixdev.lunixrecipes.LunixRecipe;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.*;

public class LunixShapelessRecipe implements LunixRecipe {
    private ShapelessRecipe ref1;
    private net.minecraft.world.item.crafting.ShapelessRecipe ref3;

    private ItemStack result;
    private List<LunixRecipeChoice> ingredients = new ArrayList<>();

    public LunixShapelessRecipe(ItemStack result) {
        this.result = result;
    }

    public LunixShapelessRecipe addIngredients(int count, LunixItemType type, int itemAmount) {
        Validate.isTrue(ingredients.size() + count <= 9, "Shapeless recipes cannot have more than 9 ingredients");
        while (count-- > 0) {
            ingredients.add(new LunixRecipeChoice(type,itemAmount));
        }
        return this;
    }

    public boolean matches(List<ItemStack> inputItemList, List<Integer> slotsToTest) {
        if (slotsToTest.size() == ingredients.size()) {
            int match = 0;

            Set<Integer> dontCheckAgain = new HashSet<>();
            LunixCraftingInv LCI = new LunixCraftingInv();
            if (ingredients.size() == slotsToTest.size()) {
                for (int slots : slotsToTest) {
                    ItemStack itemstack = inputItemList.get(LCI.getIndex(slots));
                    for (int i = 0;i<ingredients.size();i++) {
                        if (!dontCheckAgain.contains(i)) {
                            LunixRecipeChoice ingredient = ingredients.get(i);
                            if (ingredient.test(itemstack)) {
                                match++;
                                dontCheckAgain.add(i);
                            }
                        }
                    }
                }
            }
            return match == this.ingredients.size();
        } else {
            return false;
        }
    }

    public List<LunixRecipeChoice> getIngredients() {
        return ingredients;
    }

    @Override
    public ItemStack getResult() {
        return result;
    }
}
