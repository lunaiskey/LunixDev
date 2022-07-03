package me.lunaiskey.lunixdev.lunixmob;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class TestMob extends Zombie {
    public TestMob(Level world) {
        super(EntityType.ZOMBIE, world);
    }
}
