package me.lunaiskey.lunixdev.lunixshop;


public class ShopCostItemStack {
    private String itemID;
    private int itemCostQuantity;

    public ShopCostItemStack(String itemID, int itemCostQuantity) {
        this.itemID = itemID;
        this.itemCostQuantity = itemCostQuantity;
    }

    public String getItemID() {
        return itemID;
    }

    public int getItemCostQuantity() {
        return itemCostQuantity;
    }

}
