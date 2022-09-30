package me.lunaiskey.lunixdev.lunixitems;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.LunixEnchant.LEnchant;
import me.lunaiskey.lunixdev.lunixitems.items.LunaSoul;
import me.lunaiskey.lunixdev.lunixitems.types.LunixMaterial;
import me.lunaiskey.lunixdev.lunixrecipes.*;
import me.lunaiskey.lunixdev.lunixitems.types.LunixItem;
import me.lunaiskey.lunixdev.lunixitems.items.*;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import me.lunaiskey.lunixdev.utils.LunixStringUtil;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ItemManager {

    private static Map<String, LunixItem> itemMap = new HashMap<>();
    private List<LunixRecipe> recipeList = new ArrayList<>();
    private List<LunixShapedRecipe> shapedRecipesList = new ArrayList<>();
    private List<LunixShapelessRecipe> shapelessRecipesList = new ArrayList<>();

    public ItemManager() {
        registerItemStacks();
        registerRecipes();
        storeRecipes();
    }

    private void registerItemStacks() {
        addLunixItem(new LunixMaterial("COAL_ORE", "&fCoal Ore", List.of("&7&oFull of Carbon!"), Rarity.COMMON, Material.COAL_ORE));
        addLunixItem(new LunixMaterial("COAL", "Coal", List.of("&7&oOne of your 5 a day!"), Rarity.COMMON, Material.COAL));
        addLunixItem(new LunixMaterial("DADS_ASHES", "&FDad's Ashes", List.of("&7&oGreat mixed with milk!"), Rarity.JUNK, Material.GUNPOWDER));
        addLunixItem(new DildoOnAStick());
        addLunixItem(new DragonCum());
        addLunixItem(new DropOfCum());
        addLunixItem(new MonthlyCrate());
        addLunixItem(new GlassPane());
        addLunixItem(new PlayerMenu());
        addLunixItem(new DuckySpine());
        addLunixItem(new LunaSoul());
        addLunixItem(new LunixMaterial("COAL_BLOCK", "Coal Block", null, Rarity.COMMON, Material.COAL_BLOCK,false,true));
        addLunixItem(new LunixMaterial("ENCHANTED_COAL","Enchanted Coal",null,Rarity.UNCOMMON,Material.COAL,true));
        addLunixItem(new LunixMaterial("IRON_INGOT","Iron Ingot",null, Rarity.COMMON,Material.IRON_INGOT));
        addLunixItem(new LunixMaterial("IRON_HELMET","Iron Helmet",null, Rarity.COMMON,Material.IRON_HELMET));
        addLunixItem(new LunixMaterial("IRON_CHESTPLATE","Iron Chestplate",null, Rarity.COMMON,Material.IRON_CHESTPLATE));
        addLunixItem(new LunixMaterial("IRON_LEGGINGS","Iron Leggings",null, Rarity.COMMON,Material.IRON_LEGGINGS));
        addLunixItem(new LunixMaterial("IRON_BOOTS","Iron Boots",null, Rarity.COMMON,Material.IRON_BOOTS));
        addLunixItem(new LunixMaterial("COMPRESSED_ASHES","Compressed Ashes",List.of("&795% of Dad."),Rarity.UNCOMMON,Material.DEAD_FIRE_CORAL_BLOCK,false,false));
        addLunixItem(new TestItem());
    }

    private void registerRecipes() {
        //addRecipe(new LunixShapedRecipe(getLunixItemStack("DILDO_ON_A_STICK")).shape("%%","%%").setIngredient('%',"COAL",2));
        //addRecipe(new LunixShapedRecipe(getLunixItemStack("COAL_ORE")).shape("*%*","%#%","*%*").setIngredient('%',"GLASS_PANE",1).setIngredient('#',"COAL",4));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("COAL_BLOCK")).shape("***","***","***").setIngredient('*',"COAL",1));
        addRecipe(new LunixShapelessRecipe(getLunixItemStack("COAL")).addIngredients(1,"COAL_ORE",1).addIngredients(1,"DRAGON_CUM",1).addIngredients(1,"DRAGON_CUM",2));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("IRON_HELMET")).shape("%%%","%*%").setIngredient('%',"IRON_INGOT",1));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("IRON_CHESTPLATE")).shape("%*%","%%%","%%%").setIngredient('%',"IRON_INGOT",1));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("IRON_LEGGINGS")).shape("%%%","%*%","%*%").setIngredient('%',"IRON_INGOT",1));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("IRON_BOOTS")).shape("%*%","%*%").setIngredient('%',"IRON_INGOT",1));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("ENCHANTED_COAL")).shape("*%*","%%%","*%*").setIngredient('%',"COAL",32));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("COMPRESSED_ASHES")).shape("%%%","%%%","%%%").setIngredient('%',"DADS_ASHES",1));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("DADS_ASHES",9)).shape("%").setIngredient('%',"COMPRESSED_ASHES",1));
        addRecipe(new LunixShapedRecipe(getLunixItemStack("IRON_CHESTPLATE")).shape(2,"%#%","%%%","%%%").setIngredient('%',"IRON_INGOT",1).setIngredient('#',Material.LEATHER_CHESTPLATE.name(),1));
    }

    private void storeRecipes() {
        recipeList.addAll(shapedRecipesList);
        recipeList.addAll(shapelessRecipesList);
    }
    public List<LunixRecipe> getRecipeList() {
        return recipeList;
    }

    private void addLunixItem(LunixItem item) {
        itemMap.put(item.getItemID(), item);
    }

    public void onBreak(BlockBreakEvent e) {
        LunixItem item = itemMap.get(NBTUtil.getLunixID(e.getPlayer().getInventory().getItemInMainHand()));
        if (item != null) {
            item.onBreak(e);
        }
    };

    public void onPlace(BlockPlaceEvent e) {
        LunixItem item = itemMap.get(NBTUtil.getLunixID(e.getItemInHand()));
        if (item != null) {
            item.onPlace(e);
        }
    };

    public void onInteract(PlayerInteractEvent e) {
        ItemStack itemStack = e.getItem(); {
            if (itemStack != null) {
                LunixItem item = itemMap.get(NBTUtil.getLunixID(itemStack));
                if (item != null) {
                    item.onInteract(e);
                }
            }
        }
    };

    public void onDrop(PlayerDropItemEvent e) {
        LunixItem item = itemMap.get(NBTUtil.getLunixID(e.getItemDrop().getItemStack()));
        if (item != null) {
            item.onDrop(e);
        }
    };

    @Nullable
    public LunixItem getLunixItem(@NotNull String itemID) {
        itemID = itemID.toUpperCase();
        if (itemMap.containsKey(itemID)) {
            return itemMap.get(itemID);
        } else {
            try {
                Material vanillaMaterial = Material.valueOf(itemID);
                return new LunixMaterial(vanillaMaterial.name(), LunixStringUtil.formatMaterialName(vanillaMaterial.name()), null,Rarity.COMMON,vanillaMaterial);
            } catch (IllegalArgumentException ignored) {
                return null;
            }
        }
    }

    public ItemStack getLunixItemStack(String itemID, int amount) {
        LunixItem item = getLunixItem(itemID);
        if (item != null) {
            ItemStack itemStack = item.getItemStack();
            itemStack.setAmount(amount);
            return itemStack;
        } else {
            return null;
        }
    }
    public ItemStack getLunixItemStack(String itemID) {
        return getLunixItemStack(itemID,1);
    }

    private void addRecipe(LunixRecipe recipe) {
        if (recipe instanceof LunixShapelessRecipe) {
            LunixShapelessRecipe shapelessRecipe = (LunixShapelessRecipe) recipe;
            List<LunixRecipeChoice> ingredientsList = shapelessRecipe.getIngredients();
            ingredientsList.sort(Collections.reverseOrder(Comparator.comparingInt(LunixRecipeChoice::getAmount)));
            shapelessRecipesList.add(shapelessRecipe);
        } else if (recipe instanceof LunixShapedRecipe) {
            LunixShapedRecipe shapedRecipe = (LunixShapedRecipe) recipe;
            shapedRecipesList.add(shapedRecipe.construct());
        }
    }

    public ItemStack updateItemStack(ItemStack itemStack) {
        List<String> lore = new ArrayList<>();
        LunixItem lunixItem = getLunixItem(NBTUtil.getLunixID(itemStack));
        if (itemStack.getType() != lunixItem.getMaterial()) {
            itemStack.setType(lunixItem.getMaterial());
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(addDisplayName(itemStack,lunixItem));
        lore = addEnchantLore(itemStack,lunixItem,lore);
        lore = addDescriptionLore(itemStack,lunixItem,lore);
        lore = addRarityLore(itemStack,lunixItem,lore);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private String addDisplayName(ItemStack item, LunixItem lunixItem) {
        String lunixName = lunixItem.getName();
        String name;
        Rarity rarity = lunixItem.getRarity();
        if (rarity != null && rarity != Rarity.NO_RARITY) {
            name = rarity.color()+lunixName;
        } else {
            name = lunixName;
        }
        return name;
    }
    private List<String> addEnchantLore(@NotNull ItemStack item, @NotNull LunixItem lunixItem, @NotNull List<String> currentLore) {
        Map<String,Integer> lunixEnchantMap = LunixDev.getLunixDev().getEnchantManager().getLEnchants(item);
        if (currentLore.size() != 0) {
            currentLore.add("");
        }
        for (String enchantID : lunixEnchantMap.keySet()) {
            LEnchant lunixEnchant = LunixDev.getLunixDev().getEnchantManager().getEnchant(enchantID);
            if (lunixEnchant != null) {
                currentLore.add(ColorUtil.color("&9"+lunixEnchant.getEnchantName()+" "+lunixEnchantMap.get(enchantID)));
            }
        }
        //currentLore.add(0,"Enchants:"+lunixEnchantMap.size());
        return currentLore;
    }

    private List<String> addDescriptionLore(@NotNull ItemStack item, @NotNull LunixItem lunixItem, @NotNull List<String> currentLore) {
        if (lunixItem.hasDescription()) {
            if (currentLore.size() != 0) {
                currentLore.add("");
            }
            currentLore.addAll(lunixItem.getDescription());
        }
        return currentLore;
    }

    private List<String> addRarityLore(@NotNull ItemStack item, @NotNull LunixItem lunixItem, @NotNull List<String> currentLore) {
        Rarity rarity = lunixItem.getRarity();
        if (rarity != null && rarity != Rarity.NO_RARITY) {
            if (currentLore.size() != 0) {
                currentLore.add("");
            }
            currentLore.add(rarity.color()+ ChatColor.BOLD+rarity.name());
        }
        return currentLore;
    }
}
