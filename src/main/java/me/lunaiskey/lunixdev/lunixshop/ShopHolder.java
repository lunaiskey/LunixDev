package me.lunaiskey.lunixdev.lunixshop;

import me.lunaiskey.lunixdev.inventories.InvType;
import me.lunaiskey.lunixdev.inventories.LunixHolder;

public class ShopHolder extends LunixHolder {
    private final String shopID;
    private int page;

    public ShopHolder(String shopID, String name, int size, InvType invType) {
        super(name, size, invType);
        this.shopID = shopID;
        this.page = 1;
    }

    public String getShopID() {
        return shopID;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
