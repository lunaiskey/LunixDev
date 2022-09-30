package me.lunaiskey.lunixdev.lunixmob;

import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class TestMob extends Zombie {
    public TestMob(Level world) {
        super(EntityType.ZOMBIE, world);
        this.setCustomName(Component.literal("Test Mob Please Ignore lol."));
    }
}
