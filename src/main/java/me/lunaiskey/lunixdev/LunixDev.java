package me.lunaiskey.lunixdev;

import me.lunaiskey.lunixdev.events.PlayerEvents;
import me.lunaiskey.lunixdev.managers.ItemManager;
import me.lunaiskey.lunixdev.managers.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LunixDev extends JavaPlugin {

    private ItemManager itemManager;
    private CommandManager commandManager;

    private static LunixDev lunixDev;
    @Override
    public void onEnable() {
        lunixDev = this;
        itemManager = new ItemManager();
        commandManager = new CommandManager();

        Bukkit.getPluginManager().registerEvents(new PlayerEvents(),this);
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

    public static LunixDev getLunixDev() {
        return lunixDev;
    }
}
