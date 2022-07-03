package me.lunaiskey.lunixdev.managers;

import me.lunaiskey.lunixdev.commands.CommandCraft;
import me.lunaiskey.lunixdev.commands.CommandLItem;
import org.bukkit.Bukkit;

public class CommandManager {

    public CommandManager() {
        registerCommands();
    }

    private void registerCommands() {
        Bukkit.getPluginCommand("litem").setExecutor(new CommandLItem());
        Bukkit.getPluginCommand("craft").setExecutor(new CommandCraft());
    }
}
