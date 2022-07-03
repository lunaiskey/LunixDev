package me.lunaiskey.lunixdev.inventories;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public interface LunixInventory {

    void init();

    Inventory getInv();

    void onClick(InventoryClickEvent e);

    void onDrag(InventoryDragEvent e);

    void onOpen(InventoryOpenEvent e);

    void onClose(InventoryCloseEvent e);


}
