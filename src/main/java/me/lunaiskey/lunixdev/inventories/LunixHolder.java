package me.lunaiskey.lunixdev.inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Objects;

public class LunixHolder implements InventoryHolder {


    private String name;
    private int size;
    private InvType type;

    public LunixHolder(String name, int size, InvType invType) {
        this.name = Objects.requireNonNullElse(name, "");
        this.size = Objects.requireNonNullElse(size, 27);
        this.type = invType;
    }


    @Override
    public Inventory getInventory() {
        return Bukkit.createInventory(this,size,name);
    }

    public InvType getType() {
        return type;
    }
}
