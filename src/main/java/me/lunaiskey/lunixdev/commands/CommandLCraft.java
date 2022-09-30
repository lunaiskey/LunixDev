package me.lunaiskey.lunixdev.commands;

import me.lunaiskey.lunixdev.inventories.craftingrelated.LunixCraftingInv;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLCraft implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("lunixdev.craft")) {
                p.openInventory(new LunixCraftingInv().getInv());
            } else {
                p.sendMessage(ColorUtil.color("&cNo Permission!"));
            }
        }
        return true;
    }
}
