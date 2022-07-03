package me.lunaiskey.lunixdev.lunixitems.items;

import me.lunaiskey.lunixdev.lunixitems.LunixItem;
import me.lunaiskey.lunixdev.lunixitems.LunixItemType;
import me.lunaiskey.lunixdev.lunixitems.Rarity;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class CoalBlock extends LunixItem {
    public CoalBlock() {
        super(LunixItemType.COAL_BLOCK, "Coal Block", null, Rarity.COMMON, Material.COAL_BLOCK);
    }

    @Override
    public void onBreak(BlockBreakEvent e) {

    }

    @Override
    public void onPlace(BlockPlaceEvent e) {

    }

    @Override
    public void onInteract(PlayerInteractEvent e) {

    }

    @Override
    public void onDrop(PlayerDropItemEvent e) {

    }
}
