package me.lunaiskey.lunixdev.lunixitems.items;

import me.lunaiskey.lunixdev.lunixitems.types.LunixItem;
import me.lunaiskey.lunixdev.lunixitems.Rarity;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class MonthlyCrate extends LunixItem {
    public MonthlyCrate() {
        super("MONTHLY_CRATE", "Monthly Crate", List.of("&7&oWill TOTALLY give you","&7&osomething good.","","&eRight Click to get scammed!"), Rarity.MYTHIC, Material.CHEST);
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

    }

    @Override
    public void onDrop(PlayerDropItemEvent e) {

    }
}
