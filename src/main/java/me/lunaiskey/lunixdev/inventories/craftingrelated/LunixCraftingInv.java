package me.lunaiskey.lunixdev.inventories.craftingrelated;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.inventories.InvType;
import me.lunaiskey.lunixdev.inventories.LunixInventory;
import me.lunaiskey.lunixdev.lunixrecipes.*;
import me.lunaiskey.lunixdev.utils.ItemBuilder;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LunixCraftingInv implements LunixInventory {

    private final String name = "Crafting";
    private final int size = 45;
    private final int outputSlot = 24;

    private final Inventory inv = new LunixCraftingHolder(name,size, InvType.CRAFTING).getInventory();

    @Override
    public void init() {
        for (int i = 0;i<size;i++) {
            switch (i) {
                case 10,11,12,19,20,21,28,29,30 -> {
                    inv.setItem(i,new ItemStack(Material.AIR));
                }
                case 24 -> {
                    inv.setItem(i,getRecipeRequired());
                }
                default -> inv.setItem(i,ItemBuilder.getDefaultFiller());
            }
        }
    }

    @Override
    public Inventory getInv() {
        init();
        return inv;
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        e.setCancelled(true);
        InventoryAction action = e.getAction();
        Player p = (Player) e.getWhoClicked();
        Inventory inv = p.getOpenInventory().getTopInventory();
        int rawSlot = e.getRawSlot();
        int slot = e.getSlot();
        LunixCraftingHolder holder = (LunixCraftingHolder) p.getOpenInventory().getTopInventory().getHolder();
        //p.sendMessage(action+"");
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            switch(action) {
                case COLLECT_TO_CURSOR,HOTBAR_MOVE_AND_READD -> e.setCancelled(true);
                case MOVE_TO_OTHER_INVENTORY -> {
                    e.setCancelled(true);
                    ItemStack item = e.getCurrentItem().clone();
                    int amount = item.getAmount();
                    ItemStack[] array = new ItemStack[9];
                    int stop = 0;
                    for (int i=0;i<9;i++) {
                        ItemStack current = inv.getItem(getSlot(i+1));
                        if (current == null) {
                            array[i] = new ItemStack(Material.AIR);
                        } else {
                            array[i] = current.clone();
                        }
                        if (array[i].getType() == Material.AIR) {
                            item.setAmount(amount);
                            array[i] = item;
                            amount = 0;
                            stop = i+1;
                            break;
                        } else if (array[i].isSimilar(item)) {
                            ItemStack k = array[i];
                            int m = k.getMaxStackSize()-k.getAmount();
                            if (amount > m) {
                                k.setAmount(k.getMaxStackSize());
                                amount = amount - m;
                            } else {
                                k.setAmount(k.getAmount()+amount);
                                amount = 0;
                                stop = i+1;
                                break;
                            }
                        }
                        stop++;
                    }
                    //p.sendMessage(stop+"");
                    for (int j = 0;j<stop;j++) {
                        inv.setItem(getSlot(j+1),array[j]);
                    }
                    if (amount <= 0) {
                        p.getInventory().setItem(slot,new ItemStack(Material.AIR));
                        checkCraft(p);
                    } else {
                        if (amount != item.getAmount()) {
                            item.setAmount(amount);
                            p.getInventory().setItem(slot,item);
                            checkCraft(p);
                        }
                    }
                }
                default -> e.setCancelled(false);
            }
        }
        switch (rawSlot) {
            case 10,11,12,19,20,21,28,29,30 -> {
                e.setCancelled(false);
                checkCraft(p);
            }
            case outputSlot -> {
                LunixRecipe rawRecipe = holder.getRecipe();
                if (rawRecipe == null) {
                    e.setCancelled(true);
                    return;
                }
                switch(action) {
                    case HOTBAR_MOVE_AND_READD,SWAP_WITH_CURSOR,COLLECT_TO_CURSOR,NOTHING -> {
                        e.setCancelled(true);
                        return;
                    }
                    case PLACE_ALL,PLACE_ONE,PLACE_SOME -> {
                        e.setCancelled(true);
                        if (p.getItemOnCursor().getAmount() + rawRecipe.getResult().getAmount() <= 64) {
                            p.getItemOnCursor().setAmount(p.getItemOnCursor().getAmount() + rawRecipe.getResult().getAmount());
                        } else {
                            return;
                        }
                    }
                    default -> {
                        e.setCancelled(false);
                    }
                }
                List<Integer> slots = getInputSlots();
                List<ItemStack> inputSlots = getInputList(inv,true);
                List<Integer> nonEmptyInputSlots = getNotEmptySlots(inv);
                boolean noLongerValid = false;
                if (rawRecipe instanceof OldLunixShapedRecipe) {
                    OldLunixShapedRecipe toCraft = (OldLunixShapedRecipe) rawRecipe;
                    for (int i = 1; i < 10; i++) {
                        ItemStack slotItem = inv.getItem(slots.get(i - 1));
                        LunixRecipeChoice choice = toCraft.getRecipeChoice(i);
                        if (slotItem != null && slotItem.getType() != Material.AIR) {
                            slotItem.setAmount(slotItem.getAmount() - choice.getAmount());
                            inv.setItem(slots.get(i - 1), slotItem);
                        }
                        noLongerValid = !toCraft.matches(inputSlots);
                    }
                } else if (rawRecipe instanceof LunixShapedRecipe) {
                    LunixShapedRecipe toCraft = (LunixShapedRecipe) rawRecipe;
                    LunixShapedRecipe.MatrixLocationInfo mli = holder.getMatrixLocationInfo();
                    List<LunixRecipeChoice> ingredientsList = toCraft.getIngredientsList();
                    for(int k = 0; k < 3; ++k) {
                        for(int l = 0; l < 3; ++l) {
                            int i1 = k - mli.getWidth();
                            int j1 = l - mli.getHeight();
                            LunixRecipeChoice recipeitemstack = LunixRecipeChoice.AIR;
                            if (i1 >= 0 && j1 >= 0 && i1 < toCraft.getWidth() && j1 < toCraft.getHeight()) {
                                LunixRecipeChoice choice;
                                if (mli.isMirrored()) {
                                    choice = ingredientsList.get(toCraft.getWidth() - i1 - 1 + j1 * toCraft.getWidth());
                                } else {
                                    choice = ingredientsList.get(i1 + j1 * toCraft.getWidth());
                                }
                                int itemSlot = getSlot((k+l*3)+1);
                                ItemStack item = inv.getItem(itemSlot);
                                //LunixDev.getLunixDev().getLogger().info(itemSlot+"");
                                //LunixDev.getLunixDev().getLogger().info(choice.getType()+""+choice.getAmount());
                                if (item != null) {
                                    item.setAmount(item.getAmount()-choice.getAmount());
                                    inv.setItem(itemSlot,item);
                                }
                            }
                        }
                    }
                    noLongerValid = !toCraft.matches(inputSlots,mli.getWidth(),mli.getHeight(),mli.isMirrored());
                } else if (rawRecipe instanceof LunixShapelessRecipe) {
                    LunixShapelessRecipe toCraft = (LunixShapelessRecipe) rawRecipe;
                    Set<Integer> alreadyModified = new HashSet<>();
                    nonEmptyInputSlots.sort(Collections.reverseOrder(Comparator.comparingInt(o->inv.getItem(o).getAmount())));
                    for(int i = 0;i < toCraft.getIngredients().size();i++) {
                        LunixRecipeChoice choice = toCraft.getIngredients().get(i);
                        for (int inputSlot : nonEmptyInputSlots) {
                            if (!alreadyModified.contains(inputSlot)) {
                                ItemStack slotItem = inv.getItem(inputSlot);
                                if (slotItem != null && choice.test(slotItem)) {
                                    slotItem.setAmount(slotItem.getAmount()-choice.getAmount());
                                    inv.setItem(inputSlot,slotItem);
                                    alreadyModified.add(inputSlot);
                                    break;
                                }
                            }
                        }
                    }
                    nonEmptyInputSlots = getNotEmptySlots(inv);
                    noLongerValid = !toCraft.matches(inputSlots,nonEmptyInputSlots);
                    //p.sendMessage(noLongerValid+""+nonEmptyInputSlots.size());

                }
                if (noLongerValid) {
                    Bukkit.getScheduler().runTask(LunixDev.getLunixDev(),()->inv.setItem(outputSlot,getInvalidRecipe()));
                    holder.setRecipe(null);
                    holder.setMatrixLocationInfo(null);
                    checkCraft(p);
                } else {
                    Bukkit.getScheduler().runTask(LunixDev.getLunixDev(),()->inv.setItem(outputSlot,rawRecipe.getResult()));
                }

            }
            /*
            default -> {
                switch (action) {
                    case MOVE_TO_OTHER_INVENTORY,HOTBAR_MOVE_AND_READD -> {e.setCancelled(true);
                    }
                }
            }
            */
        }
    }

    @Override
    public void onDrag(InventoryDragEvent e) {
        Set<Integer> rawChangedSlots = e.getRawSlots();
        boolean craftModified = false;
        for (int slot : rawChangedSlots) {
            switch (slot) {
                case 0,1,2,3,4,5,6,7,8,9,13,14,15,16,17,18,22,23,24,25,26,27,31,32,33,34,35,36,37,38,39,40,41,42,43,44 -> e.setCancelled(true);
                case 10,11,12,19,20,21,28,29,30 -> craftModified = true;
            }
        }
        if (craftModified) {
            checkCraft((Player) e.getWhoClicked());
        }
        //e.setCancelled(true);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
    }

    public void checkCraft(Player p) {
        Inventory inv = p.getOpenInventory().getTopInventory();
        List<LunixRecipe> recipes = LunixDev.getLunixDev().getItemManager().getRecipeList();
        List<Integer> slots = getInputSlots();
        LunixCraftingHolder holder = (LunixCraftingHolder) inv.getHolder();
        LunixRecipe currRecipe = holder.getRecipe();
        Bukkit.getScheduler().runTask(LunixDev.getLunixDev(),()->{
            if (currRecipe != null) {
                if (currRecipe instanceof OldLunixShapedRecipe) {
                    OldLunixShapedRecipe recipe = (OldLunixShapedRecipe) currRecipe;
                    if (recipe.matches(getInputList(inv,true))) {
                        return;
                    }
                } else if (currRecipe instanceof LunixShapedRecipe) {
                    LunixShapedRecipe recipe = (LunixShapedRecipe) currRecipe;
                    LunixShapedRecipe.MatrixLocationInfo mli = holder.getMatrixLocationInfo();
                    if (mli == null) {
                        mli = recipe.matches(getInputList(inv,true));
                        holder.setMatrixLocationInfo(mli);
                    }
                    if (recipe.matches(getInputList(inv,true), mli.getWidth(), mli.getHeight(), mli.isMirrored())) {
                        return;
                    }
                } else if (currRecipe instanceof LunixShapelessRecipe) {
                    LunixShapelessRecipe recipe = (LunixShapelessRecipe) currRecipe;
                    if (recipe.matches(getInputList(inv,true),getNotEmptySlots(inv))) {
                        return;
                    }
                }
            }
            boolean empty = true;
            for (int j = 1;j<10;j++) {
                ItemStack slotItem = inv.getItem(slots.get(j-1));
                if (slotItem != null && slotItem.getType() != Material.AIR) {
                    empty = false;
                    break;
                }
            }
            if (!empty) {
                boolean match = false;
                LunixRecipe matchedRecipe = null;
                for (int i = 0;i<recipes.size();i++) {
                    LunixRecipe rawRecipe = recipes.get(i);
                    //LunixDev.getLunixDev().getLogger().info(recipe.toString());
                    if (rawRecipe instanceof OldLunixShapedRecipe) {
                        OldLunixShapedRecipe recipe = (OldLunixShapedRecipe) rawRecipe;
                        if (recipe.matches(getInputList(inv,true))) {
                            match = true;
                            matchedRecipe = recipe;
                        }
                    } else if (rawRecipe instanceof LunixShapedRecipe) {
                        LunixShapedRecipe recipe = (LunixShapedRecipe) rawRecipe;
                        LunixShapedRecipe.MatrixLocationInfo mli = recipe.matches(getInputList(inv,true));
                        if (mli != null) {
                            match = true;
                            matchedRecipe = recipe;
                            holder.setMatrixLocationInfo(mli);
                        }
                    } else if (rawRecipe instanceof LunixShapelessRecipe) {
                        LunixShapelessRecipe recipe = (LunixShapelessRecipe) rawRecipe;
                        if (recipe.matches(getInputList(inv,true),getNotEmptySlots(inv))) {
                            match = true;
                            matchedRecipe = recipe;
                        }

                    }
                    if (match) {
                        break;
                    }
                }
                if (match) {
                    if (matchedRecipe instanceof LunixShapedRecipe) {
                        LunixShapedRecipe recipe = (LunixShapedRecipe) matchedRecipe;
                        if (recipe.getPreserveSlot() >= 1 && recipe.getPreserveSlot() <= recipe.getHeight()*recipe.getWidth()) {
                            LunixShapedRecipe.MatrixLocationInfo mli = holder.getMatrixLocationInfo();
                            int currSlot = 0;
                            for(int k = 0; k < 3; ++k) {
                                for(int l = 0; l < 3; ++l) {
                                    int i1 = k - mli.getWidth();
                                    int j1 = l - mli.getHeight();
                                    if (i1 >= 0 && j1 >= 0 && i1 < recipe.getWidth() && j1 < recipe.getHeight()) {
                                        currSlot++;
                                        if (currSlot == recipe.getPreserveSlot()) {
                                            int invSlot = l+(k*3);
                                            ItemStack slot = inv.getItem(getSlot(invSlot+1));
                                            //p.sendMessage(getSlot(invSlot+1)+"");
                                            if (slot != null && slot.getType() != Material.AIR) {
                                                slot = slot.clone();
                                                slot = NBTUtil.addLunixID(slot,NBTUtil.getLunixID(matchedRecipe.getResult()));
                                                slot = LunixDev.getLunixDev().getItemManager().updateItemStack(slot);
                                                inv.setItem(outputSlot,slot);
                                            } else {
                                                inv.setItem(outputSlot,matchedRecipe.getResult());
                                            }
                                            break;
                                        }
                                    }
                                }
                                if (currSlot == recipe.getPreserveSlot()) {
                                    break;
                                }
                            }
                        } else {
                            inv.setItem(outputSlot,matchedRecipe.getResult());
                        }
                    } else {
                        inv.setItem(outputSlot,matchedRecipe.getResult());
                    }
                    holder.setRecipe(matchedRecipe);
                } else {
                    inv.setItem(outputSlot,getInvalidRecipe());
                    holder.setRecipe(null);
                    holder.setMatrixLocationInfo(null);
                }
            } else {
                inv.setItem(outputSlot,getRecipeRequired());
            }
        });
    }

    public List<Integer> getInputSlots() {
        List<Integer> list = new ArrayList<>();
        list.add(10);list.add(11);list.add(12);
        list.add(19);list.add(20);list.add(21);
        list.add(28);list.add(29);list.add(30);
        return list;
    }

    public int getIndex(int slot) {
        return switch(slot) {
            case 10,11,12 -> slot - 10;
            case 19,20,21 -> slot - 16;
            case 28,29,30 -> slot - 22;
            default -> -1;
        };
    }

    public int getSlot(int index) {
        return switch (index) {
            case 1,2,3 -> index + 9;
            case 4,5,6 -> index + 15;
            case 7,8,9 -> index + 21;
            default -> -1;
        };
    }



    public ItemStack getRecipeRequired() {
        return ItemBuilder.createItem("&cRecipe Required",Material.BARRIER,null);
    }

    public ItemStack getInvalidRecipe() {
        return ItemBuilder.createItem("&cInvalid Recipe",Material.BARRIER,null);
    }

    public List<ItemStack> getInputList(Inventory inv,boolean includeAir) {
        List<ItemStack> slotItems = new ArrayList<>();
        for (int slot : getInputSlots()) {
            ItemStack slotItem = inv.getItem(slot);
            if (slotItem == null) {
                if (includeAir) {
                    slotItems.add(new ItemStack(Material.AIR));
                }
            } else {
                slotItems.add(slotItem);
            }
        }
        return slotItems;
    }

    public List<Integer> getNotEmptySlots(Inventory inv) {
        List<Integer> list = new ArrayList<>();
        for(int slot : getInputSlots()) {
            ItemStack item = inv.getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                list.add(slot);
            }
        }
        return list;
    }
}
