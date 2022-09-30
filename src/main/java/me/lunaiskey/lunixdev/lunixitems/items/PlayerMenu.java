package me.lunaiskey.lunixdev.lunixitems.items;

import me.lunaiskey.lunixdev.lunixitems.Rarity;
import me.lunaiskey.lunixdev.lunixitems.types.LunixItem;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class PlayerMenu extends LunixItem {
    public PlayerMenu() {
        super("PLAYER_MENU", "&aPlayer Menu", List.of(ColorUtil.color("&7&oNo","","&eClick to open!")), Rarity.NO_RARITY, Material.NETHER_STAR);
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
