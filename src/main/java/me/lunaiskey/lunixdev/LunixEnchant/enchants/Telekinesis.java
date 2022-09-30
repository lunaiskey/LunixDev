package me.lunaiskey.lunixdev.LunixEnchant.enchants;

import me.lunaiskey.lunixdev.LunixEnchant.LEnchant;
import org.bukkit.event.block.BlockBreakEvent;

public class Telekinesis extends LEnchant {
    public Telekinesis() {
        super("TELEKINESIS", "Telekinesis",1);
    }

    @Override
    public BlockBreakEvent onBreak(BlockBreakEvent e, int level) {
        return e;
    }
}
