package me.lunaiskey.lunixdev.lunixitems;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class LunixMaterial extends LunixItem{

    private boolean placeable;

    public LunixMaterial(LunixItemType type, String name, List<String> description, Rarity rarity, Material material, boolean isPlaceable) {
        super(type, name, description, rarity, material);
        this.placeable = isPlaceable;
    }

    public LunixMaterial(LunixItemType type, String name, List<String> description, Rarity rarity, Material material) {
        this(type, name, description, rarity, material, false);
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
