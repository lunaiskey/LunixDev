package me.lunaiskey.lunixdev.events;

import me.lunaiskey.lunixdev.inventories.LunixHolder;
import me.lunaiskey.lunixdev.inventories.craftingrelated.LunixCraftingInv;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.EventExecutor;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {

    }

    @EventHandler
    public void onClose(BlockPlaceEvent e) {

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {

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
            }
        }
    }
}
