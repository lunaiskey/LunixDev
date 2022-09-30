package me.lunaiskey.lunixdev.events;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.inventories.LunixHolder;
import me.lunaiskey.lunixdev.inventories.craftingrelated.LunixCraftingInv;
import me.lunaiskey.lunixdev.lunixitems.ItemManager;
import me.lunaiskey.lunixdev.lunixshop.Shop;
import me.lunaiskey.lunixdev.lunixshop.ShopGUI;
import me.lunaiskey.lunixdev.lunixshop.ShopHolder;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.EventExecutor;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PlayerEvents implements Listener {
    private LunixDev plugin;
    private ItemManager itemManager;
    public PlayerEvents(LunixDev plugin) {
        this.plugin = plugin;
        this.itemManager = plugin.getItemManager();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        itemManager.onBreak(e);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        itemManager.onPlace(e);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        itemManager.onInteract(e);
        Player player = e.getPlayer();
        /*
        player.sendMessage("Action: "+e.getAction());
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(date);
        player.sendMessage(dateString);

         */
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        itemManager.onDrop(e);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) {return;}
        Inventory inv = e.getView().getTopInventory();
        InventoryHolder holder = inv.getHolder();
        if (holder instanceof LunixHolder) {
            LunixHolder lunixHolder = (LunixHolder) holder;
            switch (lunixHolder.getType()) {
                case CRAFTING -> new LunixCraftingInv().onClick(e);
                case SHOP -> {
                    ShopHolder shopHolder = (ShopHolder) lunixHolder;
                    Shop shop = LunixDev.getLunixDev().getShopManager().getShop(shopHolder.getShopID());
                    if (shop != null) {
                        new ShopGUI(shop).onClick(e);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        Inventory inv = e.getView().getTopInventory();
        InventoryHolder holder = inv.getHolder();
        if (holder instanceof LunixHolder) {
            LunixHolder lunixHolder = (LunixHolder) holder;
            switch (lunixHolder.getType()) {
                case CRAFTING -> new LunixCraftingInv().onDrag(e);
                case SHOP -> {
                    ShopHolder shopHolder = (ShopHolder) lunixHolder;
                    Shop shop = LunixDev.getLunixDev().getShopManager().getShop(shopHolder.getShopID());
                    if (shop != null) {
                        new ShopGUI(shop).onDrag(e);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        Inventory inv = e.getView().getTopInventory();
        InventoryHolder holder = inv.getHolder();
        if (holder instanceof LunixHolder) {
            LunixHolder lunixHolder = (LunixHolder) holder;
            switch (lunixHolder.getType()) {
                case CRAFTING -> new LunixCraftingInv().onOpen(e);
                case SHOP -> {
                    ShopHolder shopHolder = (ShopHolder) lunixHolder;
                    Shop shop = LunixDev.getLunixDev().getShopManager().getShop(shopHolder.getShopID());
                    if (shop != null) {
                        new ShopGUI(shop).onOpen(e);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Inventory inv = e.getView().getTopInventory();
        InventoryHolder holder = inv.getHolder();
        if (holder instanceof LunixHolder) {
            LunixHolder lunixHolder = (LunixHolder) holder;
            switch (lunixHolder.getType()) {
                case CRAFTING -> new LunixCraftingInv().onClose(e);
                case SHOP -> {
                    ShopHolder shopHolder = (ShopHolder) lunixHolder;
                    Shop shop = LunixDev.getLunixDev().getShopManager().getShop(shopHolder.getShopID());
                    if (shop != null) {
                        new ShopGUI(shop).onClose(e);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        //e.getPlayer().sendMessage("Test1");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = ((CraftEntity) event.getEntity()).getHandle();

    }
}
