package me.lunaiskey.lunixdev.managers;

import me.lunaiskey.lunixdev.commands.CommandLCraft;
import me.lunaiskey.lunixdev.commands.CommandLEnchant;
import me.lunaiskey.lunixdev.commands.CommandLItem;
import me.lunaiskey.lunixdev.commands.CommandLShop;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

public class CommandManager {

    public CommandManager() {
        registerCommands();
    }

    private void registerCommands() {
        addCommand("litem",new CommandLItem());
        addCommand("lcraft",new CommandLCraft());
        addCommand("lshop",new CommandLShop());
        addCommand("lenchant", new CommandLEnchant());
    }

    private void addCommand(String name, CommandExecutor executor) {
        Bukkit.getPluginCommand(name).setExecutor(executor);
    }
}
