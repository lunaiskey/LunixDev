package me.lunaiskey.lunixdev.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NBTUtil {

    private static final String lunixDataID = "LunixData";

    public static CompoundTag getBaseCompoundTag(ItemStack item) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        return nmsStack.getOrCreateTag();
    }

    public static ItemStack addCustomCompoundToBaseCompound(ItemStack item, String containerName, CompoundTag tag) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag itemTag = nmsStack.getOrCreateTag();
        itemTag.put(containerName,tag);
        nmsStack.setTag(itemTag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static boolean hasCustomTagContainer(ItemStack item, String containerName) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        if (!nmsStack.hasTag()) {
            return false;
        }
        CompoundTag itemTag = nmsStack.getTag();
        assert itemTag != null;
        return itemTag.contains(containerName);
    }

    public static ItemStack setBaseCompoundTag(ItemStack item, CompoundTag tag) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        nmsStack.setTag(tag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static ItemStack addLunixDataCompoundTag(ItemStack item) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag itemTag = nmsStack.getOrCreateTag();
        CompoundTag pyrexDataTag = new CompoundTag();
        if (itemTag.getAllKeys().contains(lunixDataID)) {
            return item;
        }
        itemTag.put(lunixDataID,pyrexDataTag);
        nmsStack.setTag(itemTag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static ItemStack addLunixData(ItemStack item, String identifier, Object value) {
        item = addLunixDataCompoundTag(item);
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag itemTag = nmsStack.getOrCreateTag();
        CompoundTag lunixDataTag = itemTag.getCompound(NBTUtil.lunixDataID);
        if (value instanceof Integer) {
            lunixDataTag.putInt(identifier,(int)value);
        }
        if (value instanceof Short) {
            lunixDataTag.putShort(identifier,(short)value);
        }
        if (value instanceof Long) {
            lunixDataTag.putLong(identifier,(long)value);
        }
        if (value instanceof Double) {
            lunixDataTag.putDouble(identifier,(double)value);
        }
        if (value instanceof String) {
            lunixDataTag.putString(identifier,(String) value);
        }
        if (value instanceof Boolean) {
            lunixDataTag.putBoolean(identifier,(boolean)value);
        }
        if (value instanceof Byte) {
            lunixDataTag.putByte(identifier,(byte) value);
        }
        if (value instanceof Float) {
            lunixDataTag.putFloat(identifier,(float) value);
        }
        if (value instanceof UUID) {
            lunixDataTag.putUUID(identifier,(UUID) value);
        }
        if (value instanceof Tag) {
            if (value instanceof CompoundTag) {
                lunixDataTag.put(identifier,(CompoundTag) value);
            } else {
                lunixDataTag.put(identifier, (Tag) value);
            }
        }
        itemTag.put(NBTUtil.lunixDataID,lunixDataTag);
        nmsStack.setTag(itemTag);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static CompoundTag getLunixDataContainer(ItemStack item) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag itemTag = nmsStack.getOrCreateTag();
        if (itemTag.contains(lunixDataID)) {
            return itemTag.getCompound(lunixDataID);
        }
        return new CompoundTag();
    }

    public static ItemStack setLunixDataContainer(ItemStack item, CompoundTag lunixDataContainer) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        CompoundTag itemTag = nmsStack.getOrCreateTag();
        itemTag.put(lunixDataID,lunixDataContainer);
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static ItemStack addLunixID(ItemStack item, String ID) {
        return addLunixData(item,"id",ID.toUpperCase());
    }

    @NotNull
    public static String getLunixID(@NotNull ItemStack item) {
        if (item.getType() == Material.AIR) {
            return item.getType().name();
        }
        CompoundTag tag = getLunixDataContainer(item);
        if (tag.contains("id")) {
            return tag.getString("id");
        } else {
            return item.getType().name();
        }
    }

}
