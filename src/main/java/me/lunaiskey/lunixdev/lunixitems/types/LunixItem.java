package me.lunaiskey.lunixdev.lunixitems.types;

import me.lunaiskey.lunixdev.lunixitems.Rarity;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import me.lunaiskey.lunixdev.utils.ItemBuilder;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public abstract class LunixItem {

    private String name;
    private String itemID;
    private List<String> description;
    private Rarity rarity;
    private Material material;
    private boolean isGlowing;

    public LunixItem(String itemID, String name, List<String> description, Rarity rarity, Material material, boolean isGlowing) {
        this.itemID = itemID.toUpperCase();
        this.name = name;
        if (description != null) {
            this.description = new ArrayList<>(description);
            this.description.replaceAll(ColorUtil::color);
        } else {
            this.description = new ArrayList<>();
        }
        this.rarity = rarity;
        this.material = material;
        this.isGlowing = isGlowing;
    }
    public LunixItem(String itemID, String name, List<String> description, Rarity rarity, Material material) {
        this(itemID,name,description,rarity,material,false);
    }

    public ItemStack getItemStack() {
        List<String> lore = new ArrayList<>();
        if (!description.isEmpty()) {
            lore.addAll(description);
            if (rarity != null && rarity != Rarity.NO_RARITY) {
                lore.add(" ");
            }
        }
        String name;
        if (rarity != null && rarity != Rarity.NO_RARITY) {
            name = rarity.color()+this.name;
            lore.add(rarity.color()+ChatColor.BOLD+rarity.name());
        } else {
            name = this.name;
        }
        ItemStack item = ItemBuilder.createItem(name,material,lore);
        ItemMeta meta = item.getItemMeta();
        if (isGlowing) {
            meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,0,true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        item = NBTUtil.addLunixID(item, itemID);
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

    public boolean hasDescription() {
        return this.description != null && this.description.size() != 0;
    }

    public String getItemID() {
        return itemID;
    }

    public Material getMaterial() {
        return material;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
