package me.lunaiskey.lunixdev.lunixitems.items;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.lunixitems.Rarity;
import me.lunaiskey.lunixdev.lunixitems.types.LunixItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class TestItem extends LunixItem {
    public TestItem() {
        super("TEST_ITEM", "Test Item Please Ignore", List.of("&7Does Stuff."), Rarity.LEGENDARY, Material.STICK, true);
    }

    @Override
    public void onBreak(BlockBreakEvent e) {

    }

    @Override
    public void onPlace(BlockPlaceEvent e) {

    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(LunixDev.getLunixDev(),()->player.giveExpLevels(1),40L);
        //player.giveExpLevels(1);
        player.sendMessage("Given 1 level.");
    }

    @Override
    public void onDrop(PlayerDropItemEvent e) {

    }
}
