package me.lunaiskey.lunixdev.inventories.craftingrelated;

import me.lunaiskey.lunixdev.inventories.InvType;
import me.lunaiskey.lunixdev.inventories.LunixHolder;
import me.lunaiskey.lunixdev.lunixrecipes.LunixRecipe;
import me.lunaiskey.lunixdev.lunixrecipes.LunixShapedRecipe;

public class LunixCraftingHolder extends LunixHolder {
    private LunixShapedRecipe.MatrixLocationInfo matrixLocationInfo;
    private LunixRecipe recipe;

    public LunixCraftingHolder(String name, int size, InvType invType) {
        super(name, size, invType);
        matrixLocationInfo = null;
        recipe = null;
    }

    public LunixRecipe getRecipe() {
        return recipe;
    }

    public LunixShapedRecipe.MatrixLocationInfo getMatrixLocationInfo() {
        return matrixLocationInfo;
    }

    public void setRecipe(LunixRecipe recipe) {
        this.recipe = recipe;
    }

    public void setMatrixLocationInfo(LunixShapedRecipe.MatrixLocationInfo matrixLocationInfo) {
        this.matrixLocationInfo = matrixLocationInfo;
    }
}
