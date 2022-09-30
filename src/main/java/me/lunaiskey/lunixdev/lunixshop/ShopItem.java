package me.lunaiskey.lunixdev.lunixshop;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.lunixitems.types.LunixItem;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopItem {

    @NotNull
    private ItemStack shopItem; //ItemStack assigned to shop item.
    private int xpLevelCost;
    private double moneyCost;
    private List<ShopCostItemStack> cost; //Everything the shop item costs.
    private boolean onlySingleTrade; //Used to stop stack item inventory to appear if true.
    private int slot;
    private int page;

    public ShopItem(@NotNull ItemStack shopItem, double moneyCost, int xpLevelCost, List<ShopCostItemStack> cost, int page, int slot, boolean onlySingleTrade) {
        this.shopItem = shopItem;
        this.moneyCost = moneyCost;
        this.xpLevelCost = xpLevelCost;
        this.slot = slot;
        this.page = page;
        this.onlySingleTrade = onlySingleTrade;
        this.cost = Objects.requireNonNullElseGet(cost, ArrayList::new);
    }

    public ShopItem(@NotNull ItemStack shopItem, double moneyCost, int xpLevelCost, List<ShopCostItemStack> cost, int page, int slot) {
        this(shopItem,moneyCost,xpLevelCost,cost,page,slot,true);
    }

    public ShopItem(@NotNull ItemStack shopItem, double moneyCost, int xpLevelCost, List<ShopCostItemStack> cost, boolean onlySingleTrade) {
        this(shopItem,moneyCost,xpLevelCost,cost,-1,-1,onlySingleTrade);
    }

    public ShopItem(@NotNull ItemStack shopItem, double moneyCost, int xpLevelCost, List<ShopCostItemStack> cost) {
        this(shopItem,moneyCost,xpLevelCost,cost,true);
    }

    public ItemStack getShopItemStack() {
        return shopItem;
    }

    public boolean isOnlySingleTrade() {
        return onlySingleTrade;
    }

    public ItemStack getShopItemStackPreview() {
        ItemStack item = getShopItemStack().clone();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add("");
        lore.add(ColorUtil.color("&7Cost:"));
        String baseText = " &8- ";
        if (isFree()) {
            lore.add(ColorUtil.color(baseText+"&aFREE"));
        } else {
            if (hasMoneyCost()) {
                BigDecimal big = new BigDecimal(moneyCost).stripTrailingZeros();
                lore.add(ColorUtil.color(baseText+"&a$"+big.toPlainString()));
            }
            if (hasXPLevelCost()) {
                lore.add(ColorUtil.color(baseText+"&b"+xpLevelCost+" XP Levels"));
            }
            if (hasItemCost()) {
                for (ShopCostItemStack cost : getItemStacksCost()) {
                    LunixItem lunixItem = LunixDev.getLunixDev().getItemManager().getLunixItem(cost.getItemID());
                    String returnItemName;
                    ItemStack returnItem;
                    if (lunixItem == null) {
                        returnItemName = ColorUtil.color("&7"+cost.getItemID()+" &c(Item Doesn't Exist?)");
                        lore.add(returnItemName);
                        continue;
                    } else {
                        returnItem = lunixItem.getItemStack();
                    }
                    if (returnItem == null) {
                        returnItem = new ItemStack(Material.AIR);
                    }
                    ItemMeta returnItemMeta = returnItem.getItemMeta();
                    if (returnItemMeta.hasDisplayName()) {
                        returnItemName = returnItemMeta.getDisplayName();
                    } else  {
                        if (returnItemMeta.hasLocalizedName()) {
                            returnItemName = returnItem.getItemMeta().getLocalizedName();
                        } else {
                            returnItemName = cost.getItemID();
                        }
                    }
                    lore.add(ColorUtil.color(baseText+returnItemName+" &8x"+cost.getItemCostQuantity()));
                }
            }
        }
        lore.add(" ");
        lore.add(ColorUtil.color("&eClick to trade!"));
        if (!isOnlySingleTrade()) {
            lore.add(ColorUtil.color("&eRight-Click to view stack options."));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public double getMoneyCost() {
        return moneyCost;
    }


    public int getXpLevelCost() {
        return xpLevelCost;
    }

    public List<ShopCostItemStack> getItemStacksCost() {
        return cost;
    }

    public boolean hasMoneyCost() {
        return moneyCost > 0;
    }

    public boolean hasXPLevelCost() {
        return xpLevelCost > 0;
    }

    public boolean hasItemCost() {
        return cost.size() > 0;
    }

    public boolean hasNonItemCost() {
        return hasMoneyCost() || hasXPLevelCost();
    }

    public boolean isFree() {
        return !hasNonItemCost() && !hasItemCost();
    }

    public int getSlot() {
        return slot;
    }

    public int getPage() {
        return page;
    }
}
