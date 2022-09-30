package me.lunaiskey.lunixdev.lunixshop;

import me.lunaiskey.lunixdev.LunixDev;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {

    private String shopID; //Shop identifier.
    private String shopTitle; //Inventory Title.
    private List<ShopItem> shopItems; //Contents of the shop
    private ShopType shopType;
    private Map<Integer, Map<Integer,ShopItem>> shopItemMap;
    private int maxPage;

    public Shop(String shopID, String shopTitle, ShopType shopType, List<ShopItem> shopItems) {
        this.shopID = shopID.toUpperCase();
        this.shopTitle = shopTitle;
        this.shopItems = shopItems;
        this.shopType = shopType;
        switch (shopType) {
            case SELECT_SLOTS -> {
                shopItemMap = new HashMap<>();
                this.maxPage = 1;
                for (ShopItem shopItem : shopItems) {
                    shopItemMap.putIfAbsent(shopItem.getPage(), new HashMap<>());
                    Map<Integer,ShopItem> subMap = shopItemMap.get(shopItem.getPage());
                    if (subMap.containsKey(shopItem.getSlot())) {
                        LunixDev.getLunixDev().getLogger().warning("Page "+shopItem.getPage()+" Slot "+shopItem.getSlot()+" already exists, skipping.");
                    } else {
                        subMap.put(shopItem.getSlot(),shopItem);
                        if (maxPage < shopItem.getPage()) {
                            this.maxPage = shopItem.getPage();
                        }
                    }
                    //shopItemMap.put(shopItem.getPage(),subMap);
                }
            }
            default -> {
                shopItemMap = new HashMap<>();
                maxPage = 0;
            }
        }

    }

    public String getShopID() {
        return shopID;
    }

    public String getShopTitle() {
        return shopTitle;
    }

    public List<ShopItem> getShopItems() {
        return shopItems;
    }

    public ShopType getShopType() {
        return shopType;
    }

    public Map<Integer, Map<Integer, ShopItem>> getShopItemMap() {
        return shopItemMap;
    }

    public int getMaxPage() {
        return maxPage;
    }
}
