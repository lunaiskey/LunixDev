package me.lunaiskey.lunixdev.lunixitems.types;

import me.lunaiskey.lunixdev.lunixitems.Rarity;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class LunixMaterial extends LunixItem {

    private boolean placeable;

    public LunixMaterial(String itemID, String name, List<String> description, Rarity rarity, Material material, boolean isGlowing, boolean isPlaceable) {
        super(itemID, name, description, rarity, material,isGlowing);
        this.placeable = isPlaceable;
    }

    public LunixMaterial(String itemID, String name, List<String> description, Rarity rarity, Material material, boolean isGlowing) {
        this(itemID, name, description, rarity, material, isGlowing,false);
    }

    public LunixMaterial(String itemID, String name, List<String> description, Rarity rarity, Material material) {
        this(itemID, name, description, rarity, material, false,false);
    }


    @Override
    public void onBreak(BlockBreakEvent e) {

    }

    @Override
    public void onPlace(BlockPlaceEvent e) {
        if (!placeable) {
            e.setCancelled(true);
        }

    }

    @Override
    public void onInteract(PlayerInteractEvent e) {

    }

    @Override
    public void onDrop(PlayerDropItemEvent e) {

    }
}
