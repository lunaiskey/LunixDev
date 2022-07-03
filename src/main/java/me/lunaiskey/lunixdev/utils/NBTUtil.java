package me.lunaiskey.lunixdev.utils;

import me.lunaiskey.lunixdev.lunixitems.LunixItemType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class NBTUtil {

    private static final String lunixDataID = "LunixData";

    public static CompoundTag getBaseTagContainer(ItemStack item) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        return nmsStack.getOrCreateTag();
    }

    public static ItemStack addCustomTagContainer(ItemStack item, String containerName, CompoundTag tag) {
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

    public static ItemStack addLunixDataContainer(ItemStack item) {
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
        item = addLunixDataContainer(item);
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

    public static ItemStack addLunixID(ItemStack item, String ID) {
        return addLunixData(item,"id",ID);
    }

    public static LunixItemType getLunixID(ItemStack item) {
        if (item == null) {
            return null;
        }
        if (item.getType() == Material.AIR) {
            return LunixItemType.AIR;
        }
        String rawType = getLunixDataContainer(item).getString("id");
        try {
            return LunixItemType.valueOf(rawType);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

}
