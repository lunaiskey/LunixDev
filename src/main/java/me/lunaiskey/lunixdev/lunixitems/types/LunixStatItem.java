package me.lunaiskey.lunixdev.lunixitems.types;

import me.lunaiskey.lunixdev.lunixitems.Rarity;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class LunixStatItem extends LunixItem {
    private Stats stats;

    public LunixStatItem(String itemID, String name, List<String> description, Rarity rarity, Material material, Stats stats) {
        super(itemID, name, description, rarity, material);
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
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
