package me.lunaiskey.lunixdev.lunixrecipes;

import com.google.common.base.Preconditions;
import me.lunaiskey.lunixdev.LunixDev;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


import java.util.*;

public class LunixShapedRecipe implements LunixRecipe {
    private int width;
    private int height;
    private ItemStack result;
    private String[] rows;
    private int preserveSlot = -1;

    private Map<Character, LunixRecipeChoice> ingredients = new HashMap<>();
    private List<LunixRecipeChoice> ingredientsList;

    public LunixShapedRecipe(ItemStack result) {
        Preconditions.checkArgument(result.getType() != Material.AIR);
        this.result = result.clone();
        this.ingredientsList = null;
    }

    public MatrixLocationInfo matches(List<ItemStack> inputItemList) {
        for(int i = 0; i <= 3 - this.width; ++i) {
            for(int j = 0; j <= 3 - this.height; ++j) {
                //if (this.matches(inputItemList, i, j, true)) {
                //    return new MatrixLocationInfo(i,j,true);
                //}

                if (this.matches(inputItemList, i, j, false)) {
                    return new MatrixLocationInfo(i,j,false);
                }
            }
        }
        //LunixDev.getLunixDev().getLogger().info(" "); //debug line
        return null;
    }

    public boolean matches(List<ItemStack> inputItemList, int i, int j, boolean flag) {
        for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < 3; ++l) {
                int i1 = k - i;
                int j1 = l - j;
                LunixRecipeChoice recipeitemstack = LunixRecipeChoice.AIR;
                if (i1 >= 0 && j1 >= 0 && i1 < this.width && j1 < this.height) {
                    if (flag) {
                        recipeitemstack = ingredientsList.get(this.width - i1 - 1 + j1 * this.width);
                    } else {
                        recipeitemstack = ingredientsList.get(i1 + (j1 * this.width));
                    }
                }

                if (!recipeitemstack.test(inputItemList.get(k + l * 3))) {
                    return false;
                }
            }
        }

        return true;
    }

    public LunixShapedRecipe shape(int preserveSlot ,String... rows) {
        this.rows = rows;
        this.height = rows.length;
        this.width = rows[0].length();
        if (preserveSlot >= 1 && preserveSlot <= 9) {
            this.preserveSlot = preserveSlot;
        }
        //LunixDev.getLunixDev().getLogger().info("Shaped: Height:"+height+" Width: "+width); //debug message
        return this;
    }
    public LunixShapedRecipe shape(String... rows) {
        return shape(-1,rows);
    }
    public LunixShapedRecipe setIngredient(char key, String itemID, int amount) {
        ingredients.put(key,new LunixRecipeChoice(itemID,amount));
        return this;
    }

    public LunixShapedRecipe construct() {
        int width = rows[0].length();
        LunixRecipeChoice[] array = new LunixRecipeChoice[rows.length * width];
        Arrays.fill(array,LunixRecipeChoice.AIR);
        List<LunixRecipeChoice> ingredientsList = new ArrayList<>(Arrays.asList(array));
        for (int i = 0;i<rows.length;i++) {
            String row = rows[i];
            for (int j = 0;j<row.length();j++) {
                ingredientsList.set(i*width+j,ingredients.getOrDefault(row.charAt(j),LunixRecipeChoice.AIR));
                //LunixDev.getLunixDev().getLogger().info("Index: "+(i+1)+":"+(j+1));
            }
        }
        this.ingredientsList = ingredientsList;
        return this;
    }
    @Override
    public ItemStack getResult() {
        return result;
    }

    public String[] getRows() {
        return rows;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getPreserveSlot() {
        return preserveSlot;
    }

    public List<LunixRecipeChoice> getIngredientsList() {
        return ingredientsList;
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

    public static class MatrixLocationInfo {
        private int startSlot;
        private boolean mirrored;
        private int width;
        private int height;

        public MatrixLocationInfo(int row,int height,boolean mirrored) {
            this.startSlot = (row*3)+height;
            this.width = row;
            this.height = height;
            this.mirrored = mirrored;
        }

        public int getStartSlot() {
            return startSlot;
        }

        public boolean isMirrored() {
            return mirrored;
        }

        public int getHeight() {
            return height;
        }

        public int getWidth() {
            return width;
        }
    }
}
