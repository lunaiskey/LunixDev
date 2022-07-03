package me.lunaiskey.lunixdev.managers;

import me.lunaiskey.lunixdev.lunixitems.LunixMaterial;
import me.lunaiskey.lunixdev.lunixitems.Rarity;
import me.lunaiskey.lunixdev.lunixrecipes.LunixRecipe;
import me.lunaiskey.lunixdev.lunixrecipes.LunixRecipeChoice;
import me.lunaiskey.lunixdev.lunixrecipes.LunixShapedRecipe;
import me.lunaiskey.lunixdev.lunixitems.LunixItem;
import me.lunaiskey.lunixdev.lunixitems.LunixItemType;
import me.lunaiskey.lunixdev.lunixitems.items.*;
import me.lunaiskey.lunixdev.lunixrecipes.LunixShapelessRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.util.*;

public class ItemManager {

    private Map<LunixItemType, LunixItem> itemMap = new HashMap<>();
    //private List<LunixRecipe> recipeList = new ArrayList<>();
    private List<LunixShapedRecipe> shapedRecipesList = new ArrayList<>();
    private List<LunixShapelessRecipe> shapelessRecipesList = new ArrayList<>();

    public ItemManager() {
        registerItems();
        registerRecipes();
    }
    private void registerItems() {
        itemMap.put(LunixItemType.COAL_ORE,new CoalOre());
        itemMap.put(LunixItemType.COAL,new Coal());
        itemMap.put(LunixItemType.DADS_ASHES,new DadsAshes());
        itemMap.put(LunixItemType.DILDO_ON_A_STICK,new DildoOnAStick());
        itemMap.put(LunixItemType.DRAGON_CUM,new DragonCum());
        itemMap.put(LunixItemType.DROP_OF_CUM,new DropOfCum());
        itemMap.put(LunixItemType.MONTHLY_CRATE,new MonthlyCrate());
        itemMap.put(LunixItemType.GLASS_PANE,new GlassPane());
        itemMap.put(LunixItemType.COAL_BLOCK,new CoalBlock());
        itemMap.put(LunixItemType.IRON_INGOT,new LunixMaterial(LunixItemType.IRON_INGOT,"Iron Ingot",null, Rarity.COMMON,Material.IRON_INGOT));
        itemMap.put(LunixItemType.IRON_HELMET,new LunixMaterial(LunixItemType.IRON_HELMET,"Iron Helmet",null, Rarity.COMMON,Material.IRON_HELMET));
        itemMap.put(LunixItemType.IRON_CHESTPLATE,new LunixMaterial(LunixItemType.IRON_CHESTPLATE,"Iron Chestplate",null, Rarity.COMMON,Material.IRON_CHESTPLATE));
        itemMap.put(LunixItemType.IRON_LEGGINGS,new LunixMaterial(LunixItemType.IRON_LEGGINGS,"Iron Leggings",null, Rarity.COMMON,Material.IRON_LEGGINGS));
        itemMap.put(LunixItemType.IRON_BOOTS,new LunixMaterial(LunixItemType.IRON_BOOTS,"Iron Boots",null, Rarity.COMMON,Material.IRON_BOOTS));
    }

    private void registerRecipes() {
        addRecipe(new LunixShapedRecipe(new CoalOre().getItemStack()).shape("*%*","%#%","*%*").setIngredient('*',LunixItemType.AIR,0).setIngredient('%',LunixItemType.GLASS_PANE,1).setIngredient('#',LunixItemType.COAL,4));
        addRecipe(new LunixShapedRecipe(new CoalBlock().getItemStack()).shape("***","***","***").setIngredient('*',LunixItemType.COAL,1));
        addRecipe(new LunixShapelessRecipe(new Coal().getItemStack()).addIngredients(1,LunixItemType.COAL_ORE,1).addIngredients(1,LunixItemType.DRAGON_CUM,1).addIngredients(1,LunixItemType.DRAGON_CUM,2));
        addRecipe(new LunixShapedRecipe(itemMap.get(LunixItemType.IRON_HELMET).getItemStack()).shape("%%%","%*%","***").setIngredient('%',LunixItemType.IRON_INGOT,1).setIngredient('*',LunixItemType.AIR,0));
        addRecipe(new LunixShapedRecipe(itemMap.get(LunixItemType.IRON_CHESTPLATE).getItemStack()).shape("%*%","%%%","%%%").setIngredient('%',LunixItemType.IRON_INGOT,1).setIngredient('*',LunixItemType.AIR,0));
        addRecipe(new LunixShapedRecipe(itemMap.get(LunixItemType.IRON_LEGGINGS).getItemStack()).shape("%%%","%*%","%*%").setIngredient('%',LunixItemType.IRON_INGOT,1).setIngredient('*',LunixItemType.AIR,0));
        addRecipe(new LunixShapedRecipe(itemMap.get(LunixItemType.IRON_BOOTS).getItemStack()).shape("%*%","%*%","***").setIngredient('%',LunixItemType.IRON_INGOT,1).setIngredient('*',LunixItemType.AIR,0));
    }

    public Map<LunixItemType, LunixItem> getItemMap() {
        return itemMap;
    }

    public List<LunixRecipe> getRecipeList() {
        List<LunixRecipe> recipeList = new ArrayList<>(shapedRecipesList);
        recipeList.addAll(shapelessRecipesList);
        return recipeList;
    }

    public ItemStack getItem(LunixItemType itemType) {
        if (itemMap.containsKey(itemType)) {
            return itemMap.get(itemType).getItemStack();
        } else if (itemType == LunixItemType.AIR){
            return new ItemStack(Material.AIR);
        } else {
            return null;
        }
    }

    private void addRecipe(LunixRecipe recipe) {
        if (recipe instanceof LunixShapelessRecipe) {
            LunixShapelessRecipe shapelessRecipe = (LunixShapelessRecipe) recipe;
            List<LunixRecipeChoice> ingredientsList = shapelessRecipe.getIngredients();
            ingredientsList.sort(Collections.reverseOrder(Comparator.comparingInt(LunixRecipeChoice::getAmount)));
            shapelessRecipesList.add(shapelessRecipe);
        } else if (recipe instanceof LunixShapedRecipe) {
            shapedRecipesList.add((LunixShapedRecipe) recipe);
        }
    }
}
