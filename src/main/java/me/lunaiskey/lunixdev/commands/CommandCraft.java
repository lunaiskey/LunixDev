package me.lunaiskey.lunixdev.commands;

import me.lunaiskey.lunixdev.inventories.craftingrelated.LunixCraftingInv;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCraft implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.openInventory(new LunixCraftingInv().getInv());
        }
        return true;
    }
}
