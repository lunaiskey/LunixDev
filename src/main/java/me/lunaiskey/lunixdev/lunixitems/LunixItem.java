package me.lunaiskey.lunixdev.lunixitems;

import me.lunaiskey.lunixdev.utils.ColorUtil;
import me.lunaiskey.lunixdev.utils.ItemBuilder;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class LunixItem {

    private String name;
    private List<String> description;
    private LunixItemType type;
    private Rarity rarity;
    private Material material;

    public LunixItem(LunixItemType type,String name, List<String> description, Rarity rarity, Material material) {
        this.name = name;
        if (description != null) {
            this.description = new ArrayList<>(description);
            this.description.replaceAll(ColorUtil::color);
        } else {
            this.description = new ArrayList<>();
        }
        this.type = type;
        this.rarity = rarity;
        this.material = material;
    }

    public ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        if (!description.isEmpty()) {
            lore.addAll(description);
            if (rarity != null) {
                lore.add(" ");
            }
        }
        String name;
        if (rarity != null) {
            name = rarity.color()+this.name;
            lore.add(rarity.color()+ChatColor.BOLD+rarity.name());
        } else {
            name = this.name;
        }
        ItemStack item = ItemBuilder.createItem(name,material,lore);
        item = NBTUtil.addLunixID(item,type.name());
        return item;
    }

    public abstract void onBreak(BlockBreakEvent e);
    public abstract void onPlace(BlockPlaceEvent e);
    public abstract void onInteract(PlayerInteractEvent e);
    public abstract void onDrop(PlayerDropItemEvent e);

    public String getName() {
        return name;
    }

    public List<String> getDescription() {
        return description;
    }

    public LunixItemType getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }


}
