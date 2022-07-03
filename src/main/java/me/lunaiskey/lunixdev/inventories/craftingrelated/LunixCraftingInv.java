package me.lunaiskey.lunixdev.inventories.craftingrelated;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.inventories.InvType;
import me.lunaiskey.lunixdev.inventories.LunixHolder;
import me.lunaiskey.lunixdev.inventories.LunixInventory;
import me.lunaiskey.lunixdev.lunixrecipes.LunixRecipe;
import me.lunaiskey.lunixdev.lunixrecipes.LunixRecipeChoice;
import me.lunaiskey.lunixdev.lunixrecipes.LunixShapedRecipe;
import me.lunaiskey.lunixdev.lunixrecipes.LunixShapelessRecipe;
import me.lunaiskey.lunixdev.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftShapelessRecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LunixCraftingInv implements LunixInventory {

    private final String name = "Crafting";
    private final int size = 45;
    private final int outputSlot = 24;

    private final Inventory inv = new LunixHolder(name,size, InvType.CRAFTING).getInventory();

    private static final Map<UUID, LunixRecipe> currentCraft = new HashMap<>();

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
        Inventory inv = e.getView().getTopInventory();
        Player p = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            switch(action) {
                case COLLECT_TO_CURSOR,MOVE_TO_OTHER_INVENTORY,HOTBAR_MOVE_AND_READD -> e.setCancelled(true);
                default -> e.setCancelled(false);
            }
        }
        switch (slot) {
            case 10,11,12,19,20,21,28,29,30 -> {
                e.setCancelled(false);
                checkCraft(p);
            }
            case outputSlot -> {
                LunixRecipe rawRecipe = currentCraft.get(p.getUniqueId());
                if (rawRecipe == null) {
                    e.setCancelled(true);
                    return;
                }
                switch(action) {
                    case HOTBAR_MOVE_AND_READD,SWAP_WITH_CURSOR,COLLECT_TO_CURSOR -> {
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
                List<ItemStack> inputSlots = getInputList(inv);
                List<Integer> nonEmptyInputSlots = getNotEmptySlots(inv);
                boolean noLongerValid = false;
                if (rawRecipe instanceof LunixShapedRecipe) {
                    LunixShapedRecipe toCraft = (LunixShapedRecipe) rawRecipe;
                    for(int i = 1;i<10;i++) {
                        ItemStack slotItem = inv.getItem(slots.get(i-1));
                        LunixRecipeChoice choice = toCraft.getRecipeChoice(i);
                        if (slotItem != null && slotItem.getType() != Material.AIR) {
                            slotItem.setAmount(slotItem.getAmount()-choice.getAmount());
                            inv.setItem(slots.get(i-1),slotItem);
                        }
                        noLongerValid = !toCraft.matches(inputSlots);
                    }
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
                    currentCraft.put(p.getUniqueId(),null);
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
        e.setCancelled(true);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {
        Player p = (Player) e.getPlayer();
        currentCraft.put(p.getUniqueId(),null);
    }

    @Override
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        currentCraft.remove(p.getUniqueId());
    }

    public void checkCraft(Player p) {
        Inventory inv = p.getOpenInventory().getTopInventory();
        List<LunixRecipe> recipes = LunixDev.getLunixDev().getItemManager().getRecipeList();
        List<Integer> slots = getInputSlots();
        Bukkit.getScheduler().runTask(LunixDev.getLunixDev(),()->{
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
                for (LunixRecipe rawRecipe : recipes) {
                    //LunixDev.getLunixDev().getLogger().info(recipe.toString());
                    if (rawRecipe instanceof LunixShapedRecipe) {
                        LunixShapedRecipe recipe = (LunixShapedRecipe) rawRecipe;
                        if (recipe.matches(getInputList(inv))) {
                            match = true;
                            matchedRecipe = recipe;
                        }

                    } else if (rawRecipe instanceof LunixShapelessRecipe) {
                        LunixShapelessRecipe recipe = (LunixShapelessRecipe) rawRecipe;
                        if (recipe.matches(getInputList(inv),getNotEmptySlots(inv))) {
                            match = true;
                            matchedRecipe = recipe;
                        }

                    }
                    if (match) {
                        break;
                    }
                }
                if (match) {
                    inv.setItem(outputSlot,matchedRecipe.getResult());
                    currentCraft.put(p.getUniqueId(),matchedRecipe);
                } else {
                    inv.setItem(outputSlot,getInvalidRecipe());
                    currentCraft.put(p.getUniqueId(),null);
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



    public ItemStack getRecipeRequired() {
        return ItemBuilder.createItem("&cRecipe Required",Material.BARRIER,null);
    }

    public ItemStack getInvalidRecipe() {
        return ItemBuilder.createItem("&cInvalid Recipe",Material.BARRIER,null);
    }

    public List<ItemStack> getInputList(Inventory inv) {
        List<ItemStack> slotItems = new ArrayList<>();
        for (int slot : getInputSlots()) {
            ItemStack slotItem = inv.getItem(slot);
            if (slotItem == null) {
                slotItems.add(new ItemStack(Material.AIR));
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
