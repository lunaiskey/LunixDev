package me.lunaiskey.lunixdev.lunixitems.items;

import me.lunaiskey.lunixdev.lunixitems.LunixItem;
import me.lunaiskey.lunixdev.lunixitems.LunixItemType;
import me.lunaiskey.lunixdev.lunixitems.Rarity;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MonthlyCrate extends LunixItem {
    public MonthlyCrate() {
        super(LunixItemType.MONTHLY_CRATE, "Monthly Crate", List.of("&7&oWill TOTALLY give you","&7&osomething good.","","&eRight Click to get scammed!"), Rarity.MYTHIC, Material.CHEST);
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
