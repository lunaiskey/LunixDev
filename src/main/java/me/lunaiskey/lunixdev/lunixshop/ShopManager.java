package me.lunaiskey.lunixdev.lunixshop;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.lunixitems.ItemManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ShopManager {
    private Map<String,Shop> shopMap = new HashMap<>();
    private Logger log;
    private ItemManager itemManager;

    public ShopManager() {
        itemManager = LunixDev.getLunixDev().getItemManager();
        log = LunixDev.getLunixDev().getLogger();
        registerShops();
    }

    public void registerShops() {
        testShop();
        testSelectSlotShop();
    }

    public void addShop(String shopID, String shopTitle, List<ShopItem> shopItems,ShopType shopType) {
        String shopIDUpper = shopID.toUpperCase();
        if (!shopMap.containsKey(shopIDUpper)) {
            Shop shop = new Shop(shopIDUpper,shopTitle,shopType,shopItems);
            shopMap.put(shopIDUpper,shop);
        }
    }

    public void testShop() {
        List<ShopItem> list = new ArrayList<>();
        list.add(new ShopItem(itemManager.getLunixItemStack("DRAGON_CUM"),100,100,
                List.of(
                        new ShopCostItemStack("DROP_OF_CUM",128),
                        new ShopCostItemStack("DROP_OF_INTEL",1)
                        )));
        list.add(new ShopItem(itemManager.getLunixItemStack("DILDO_ON_A_STICK"),5,0, null));
        list.add(new ShopItem(itemManager.getLunixItemStack("MONTHLY_CRATE"),Long.MAX_VALUE,0,null));
        list.add(new ShopItem(itemManager.getLunixItemStack("COAL_BLOCK"),0,0,
                List.of(new ShopCostItemStack("COAL",9)
                )));
        addShop("testshop","Test Shop",list,ShopType.DEFAULT);
    }

    public void testSelectSlotShop() {
        List<ShopItem> list = new ArrayList<>();
        list.add(new ShopItem(itemManager.getLunixItemStack("ENCHANTED_COAL"),50,0,null,1,22));
        list.add(new ShopItem(itemManager.getLunixItemStack("COAL_BLOCK"),50,0,null,2,22));
        addShop("testselectslots","Test Select Slot Shop",list,ShopType.SELECT_SLOTS);
    }

    @Nullable
    public Shop getShop(String shopID) {
        shopID = shopID.toUpperCase();
        if (shopExists(shopID)) {
            return shopMap.get(shopID);
        } else {
            return null;
        }
    }

    public void openShop(Player player,@NotNull Shop shop) {
        player.openInventory(new ShopGUI(shop).getInv());
    }

    public void openShop(Player player, String shopID) {
        Shop shop = getShop(shopID);
        if (shop != null) {
            openShop(player,shop);
        }
    }

    public boolean shopExists(@NotNull String shopID) {
        return shopMap.containsKey(shopID.toUpperCase());
    }
}
