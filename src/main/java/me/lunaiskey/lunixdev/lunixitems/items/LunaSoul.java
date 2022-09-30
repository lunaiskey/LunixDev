package me.lunaiskey.lunixdev.lunixitems.items;

import me.lunaiskey.lunixdev.lunixitems.Rarity;
import me.lunaiskey.lunixdev.lunixitems.types.LunixItem;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class LunaSoul extends LunixItem {
    public LunaSoul() {
        super("LUNA_SOUL", "Luna's Soul", List.of("&7&oThey don't have a soul really..."), Rarity.RARE, Material.POPPY, true);
    }

    @Override
    public void onBreak(BlockBreakEvent e) {

    }

    @Override
    public void onPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onDrop(PlayerDropItemEvent e) {

    }
}
