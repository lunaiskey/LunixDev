package me.lunaiskey.lunixdev.lunixitems;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class LunixStatsItem extends LunixItem{

    private Stats stats;

    public LunixStatsItem(LunixItemType type, String name, List<String> description,Rarity rarity, Material material, Stats stats) {
        super(type, name, description, rarity, material);
        this.stats = stats;
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

    public Stats getStats() {
        return stats;
    }
}
