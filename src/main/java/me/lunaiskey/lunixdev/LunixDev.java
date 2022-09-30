package me.lunaiskey.lunixdev;

import me.lunaiskey.lunixdev.LunixEnchant.LEnchantManager;
import me.lunaiskey.lunixdev.events.PlayerEvents;
import me.lunaiskey.lunixdev.lunixitems.ItemManager;
import me.lunaiskey.lunixdev.lunixshop.ShopManager;
import me.lunaiskey.lunixdev.managers.CommandManager;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class LunixDev extends JavaPlugin {

    private ItemManager itemManager;
    private CommandManager commandManager;
    private LEnchantManager enchantManager;
    private ShopManager shopManager = null;
    private Economy economy;

    private static LunixDev lunixDev;
    @Override
    public void onEnable() {
        lunixDev = this;
        Logger log = lunixDev.getLogger();
        itemManager = new ItemManager();
        enchantManager = new LEnchantManager();
        if (!registerVault()) {
            log.severe(ColorUtil.color("&cVault dependency has failed to load. Economy Related features have been disabled."));
        } else {
            //add all economy features.
            shopManager = new ShopManager(); log.info("Enabling shop manager");

        }
        commandManager = new CommandManager();
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(this),this);
    }

    @Override
    public void onDisable() {

    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public LEnchantManager getEnchantManager() {
        return enchantManager;
    }

    public static LunixDev getLunixDev() {
        return lunixDev;
    }

    private boolean registerVault() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public boolean isEconomyEnabled() {
        return economy != null;
    }

    public Economy getEconomy() {
        return economy;
    }


}
