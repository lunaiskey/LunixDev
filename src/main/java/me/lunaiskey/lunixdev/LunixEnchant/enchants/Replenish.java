package me.lunaiskey.lunixdev.LunixEnchant.enchants;

import me.lunaiskey.lunixdev.LunixEnchant.LEnchant;
import org.bukkit.event.block.BlockBreakEvent;

public class Replenish extends LEnchant {
    public Replenish() {
        super("REPLENISH", "Replenish",1);
    }

    @Override
    public BlockBreakEvent onBreak(BlockBreakEvent e, int level) {
        return e;
    }
}
