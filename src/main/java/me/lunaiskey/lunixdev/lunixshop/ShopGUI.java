package me.lunaiskey.lunixdev.lunixshop;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.inventories.InvType;
import me.lunaiskey.lunixdev.inventories.LunixInventory;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import me.lunaiskey.lunixdev.utils.ItemBuilder;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopGUI implements LunixInventory {

    private String name;
    private final int size = 54;
    private Shop shop;
    private List<ShopItem> shopContents;
    private ShopType shopType;
    private String shopID;

    private final Inventory inv;


    public ShopGUI(Shop shop) {
        this.name = shop.getShopTitle();
        this.shop = shop;
        this.shopContents = shop.getShopItems();
        this.shopType = shop.getShopType();
        this.shopID = shop.getShopID();
        this.inv = new ShopHolder(shopID,name,size, InvType.SHOP).getInventory();
    }
    @Override
    public void init() {
        Map<Integer,Map<Integer,ShopItem>> shopItemBaseMap = shop.getShopItemMap();
        Map<Integer,ShopItem> shopItemMap;
        if (shopItemBaseMap == null) {
            shopItemMap = new HashMap<>();
        } else {
            shopItemMap = shopItemBaseMap.get(1);
        }
        for (int i = 0;i<size;i++) {
            switch (shopType) {
                case DEFAULT -> {
                    switch (i) {
                        case 0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53 -> inv.setItem(i, ItemBuilder.getDefaultFiller());
                        default -> {
                            int index = getIndex(i);
                            if (shopContents.size() >= index+1) {
                                ShopItem shopItem = shopContents.get(index);
                                inv.setItem(i,shopItem.getShopItemStackPreview());
                            }
                        }
                    }
                }
                case SELECT_SLOTS -> {
                    if (i == 8) {
                        if (shop.getMaxPage() > 1) {
                            inv.setItem(i, ItemBuilder.getNextPage(2));
                        } else {
                            inv.setItem(i,ItemBuilder.getDefaultFiller());
                        }
                    } else {
                        if (shopItemMap != null) {
                            if (shopItemMap.containsKey(i)) {
                                inv.setItem(i, shopItemMap.get(i).getShopItemStackPreview());
                            } else {
                                inv.setItem(i, ItemBuilder.getDefaultFiller());
                            }
                        } else {
                            inv.setItem(i, ItemBuilder.getDefaultFiller());
                        }
                    }
                }
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
        Player player = (Player) e.getWhoClicked();
        Inventory inv = player.getOpenInventory().getTopInventory();
        ShopHolder holder = (ShopHolder) inv.getHolder();
        //player.sendMessage("Type: "+e.getClickedInventory().getType());
        if (e.getClickedInventory().getType() == InventoryType.CHEST) {
            int slot = e.getRawSlot();
            ItemStack item = e.getInventory().getItem(slot);
            if (item != null && item.getType() != Material.AIR) {
                switch (shopType) {
                    case DEFAULT -> {
                        int index = getIndex(slot);
                        ShopItem shopItem = shopContents.get(index);
                        attemptPurchase(player,shopItem);
                    }
                    case SELECT_SLOTS -> {
                        Map<Integer,Map<Integer,ShopItem>> shopItemMap = shop.getShopItemMap();
                        switch (slot) {
                            case 0,8 -> {
                                boolean changed = false;
                                if (slot == 8) {
                                    if (holder.getPage() < shop.getMaxPage()) {
                                        holder.setPage(holder.getPage()+1);
                                        changed = true;
                                    }
                                }
                                if (slot == 0) {
                                    if (holder.getPage() > 1) {
                                        holder.setPage(holder.getPage()-1);
                                        changed = true;
                                    }
                                }
                                //LunixDev.getLunixDev().getLogger().info(holder.getPage()+"");
                                if (changed) {
                                    for (int i = 0;i<size;i++) {
                                        switch (i) {
                                            case 0 -> inv.setItem(i,holder.getPage() > 1 ? ItemBuilder.getPreviousPage(holder.getPage()-1) : ItemBuilder.getDefaultFiller());
                                            case 8 -> inv.setItem(i, holder.getPage() < shop.getMaxPage() ? ItemBuilder.getNextPage(holder.getPage()+1) : ItemBuilder.getDefaultFiller());
                                            default -> {
                                                Map<Integer,ShopItem> shopItemSubMap = shopItemMap.get(holder.getPage());
                                                if (shopItemSubMap != null) {
                                                    ShopItem shopItem = shopItemSubMap.get(i);
                                                    if (shopItem != null) {
                                                        inv.setItem(i,shopItem.getShopItemStackPreview());
                                                    } else {
                                                        inv.setItem(i,ItemBuilder.getDefaultFiller());

                                                    }
                                                } else {
                                                    inv.setItem(i,ItemBuilder.getDefaultFiller());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            default -> {
                                if (shopItemMap != null) {
                                    Map<Integer,ShopItem> shopItemSubMap = shopItemMap.get(holder.getPage());
                                    if (shopItemSubMap != null) {
                                        ShopItem shopItem = shopItemSubMap.get(slot);
                                        if (shopItem != null) {
                                            attemptPurchase(player,shopItem);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    @Override
    public void onDrag(InventoryDragEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onOpen(InventoryOpenEvent e) {

    }

    @Override
    public void onClose(InventoryCloseEvent e) {

    }

    private int getIndex(int invSlot) {
        return switch(invSlot) {
            case 10,11,12,13,14,15,16 -> invSlot-10;
            case 19,20,21,22,23,24,25 -> invSlot-12;
            case 28,29,30,31,32,33,34 -> invSlot-14;
            case 37,38,39,40,41,42,43 -> invSlot-16;
            default -> -1;
        };
    }

    public void attemptPurchase(Player player, ShopItem shopItem, int amount) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(ColorUtil.color("&cNot enough space in inventory to purchase this."));
            return;
        }
        Economy econ = LunixDev.getLunixDev().getEconomy();
        double moneyBalance = econ.getBalance(player);
        int xpLevelBalance = player.getLevel();

        double moneyCost = shopItem.getMoneyCost();
        int xpLevelCost = shopItem.getXpLevelCost();
        boolean hasEnoughMoney = (moneyBalance - moneyCost) >= 0;
        boolean hasEnoughXPLevels = (xpLevelBalance - xpLevelCost) >= 0;
        if (!hasEnoughMoney) {
            player.sendMessage(ColorUtil.color("&cYou don't have enough Money to purchase this."));
            return;
        }
        if (!hasEnoughXPLevels) {
            player.sendMessage(ColorUtil.color("&cYou don't have enough XP Levels to purchase this."));
            return;
        }
        if (shopItem.hasItemCost()) {
            List<ShopCostItemStack> itemstacksCost = shopItem.getItemStacksCost();
            Map<String,Integer> lunixItemCount = new HashMap<>();
            //find quantities of every item.
            Inventory inventory = player.getInventory();
            for (ItemStack stack : inventory.getContents()) {
                if (stack != null) {
                    String itemID = NBTUtil.getLunixID(stack);
                    if (lunixItemCount.containsKey(itemID)) {
                        lunixItemCount.put(itemID,lunixItemCount.get(itemID)+stack.getAmount());
                    } else {
                        lunixItemCount.put(itemID,stack.getAmount());
                    }
                }
            }
            //check if player has enough of items to continue
            Map<String,Integer> itemRemovalCount = new HashMap<>();
            for (ShopCostItemStack shopCostItemStack : itemstacksCost) {
                String itemID = shopCostItemStack.getItemID();
                int itemAmount = shopCostItemStack.getItemCostQuantity();
                int inventoryAmount = lunixItemCount.getOrDefault(itemID, 0);
                if (!(inventoryAmount - itemAmount >= 0)) {
                    player.sendMessage(ColorUtil.color("&cYou don't have enough "+itemID+" to purchase this."));
                    return;
                }
                itemRemovalCount.put(itemID,itemAmount);
            }
            //remove items from inventory
            ItemStack[] contents = inventory.getContents().clone();
            for (int i = 0; i < inventory.getContents().length;i++) {
                if (itemRemovalCount.size() == 0) {
                    break;
                }
                ItemStack stack = contents[i];
                if (stack != null) {
                    String itemID = NBTUtil.getLunixID(stack);
                    int itemAmount = stack.getAmount();
                    int currentRemovalCount = itemRemovalCount.getOrDefault(itemID,0);
                    if (currentRemovalCount > 0) {
                        if (currentRemovalCount >= itemAmount) {
                            itemRemovalCount.replace(itemID,currentRemovalCount-itemAmount);
                            contents[i] = null;
                        } else {
                            itemRemovalCount.remove(itemID);
                            stack.setAmount(itemAmount-currentRemovalCount);
                            contents[i] = stack;
                        }
                    }
                }
            }
            inventory.setContents(contents);
        }
        if (shopItem.hasMoneyCost()) {
            econ.withdrawPlayer(player,moneyCost);
        }
        if (shopItem.hasXPLevelCost()) {
            player.setLevel(xpLevelBalance-xpLevelCost);
        }
        //give items to player
        player.getInventory().addItem(shopItem.getShopItemStack());

        player.sendMessage(ColorUtil.color("&aSuccessfully bought "+shopItem.getShopItemStack().getItemMeta().getDisplayName()));
    }

    public void attemptPurchase(Player player, @NotNull ShopItem shopItem) {
        attemptPurchase(player, shopItem, 1);
    }
}
