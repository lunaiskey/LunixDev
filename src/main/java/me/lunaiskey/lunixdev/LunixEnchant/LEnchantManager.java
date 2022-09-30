package me.lunaiskey.lunixdev.LunixEnchant;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.LunixEnchant.enchants.Delicate;
import me.lunaiskey.lunixdev.LunixEnchant.enchants.Replenish;
import me.lunaiskey.lunixdev.LunixEnchant.enchants.Telekinesis;
import me.lunaiskey.lunixdev.utils.NBTUtil;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class LEnchantManager {
    private Map<String,LEnchant> enchantMap = new HashMap<>();

    public LEnchantManager() {
        registerEnchant();
    }

    private void registerEnchant() {
        addEnchant(new Telekinesis());
        addEnchant(new Replenish());
        addEnchant(new Delicate());
    }

    private void addEnchant(LEnchant enchant) {
        enchantMap.put(enchant.getEnchantID(),enchant);
    }

    public ItemStack addEnchant(ItemStack item, String enchantID, int level) {
        CompoundTag lunixCompound = NBTUtil.getLunixDataContainer(item);
        CompoundTag enchantCompound;
        if (lunixCompound.contains("enchants")) {
            enchantCompound = lunixCompound.getCompound("enchants");
        } else {
            enchantCompound = new CompoundTag();
        }
        if (level != 0) {
            enchantCompound.putInt(enchantID.toLowerCase(),level);
            lunixCompound.put("enchants",enchantCompound);
        } else {
            enchantCompound.remove(enchantID.toLowerCase());
            if (enchantCompound.size() == 0) {
                lunixCompound.remove("enchants");
            }
        }
        item = NBTUtil.setLunixDataContainer(item,lunixCompound);
        item = LunixDev.getLunixDev().getItemManager().updateItemStack(item);
        return item;
    }

    public Map<String,Integer> getLEnchants(ItemStack item) {
        CompoundTag lunixCompound = NBTUtil.getLunixDataContainer(item);
        CompoundTag enchantCompound;
        if (!lunixCompound.contains("enchants")) {
            enchantCompound = new CompoundTag();
        } else {
            enchantCompound = lunixCompound.getCompound("enchants");
        }
        Map<String,Integer> enchantMap = new HashMap<>();
        for (String enchantID : enchantCompound.getAllKeys()) {
            enchantMap.put(enchantID.toUpperCase(),enchantCompound.getInt(enchantID));
        }
        return enchantMap;
    }

    @Nullable
    public LEnchant getEnchant(@NotNull String enchantID) {
        enchantID = enchantID.toUpperCase();
        return enchantMap.getOrDefault(enchantID,null);
    }
}
