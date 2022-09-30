package me.lunaiskey.lunixdev.commands;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.LunixEnchant.LEnchant;
import me.lunaiskey.lunixdev.LunixEnchant.LEnchantManager;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CommandLEnchant implements CommandExecutor {
    private LEnchantManager enchantManager = LunixDev.getLunixDev().getEnchantManager();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("lunixdev.admin")) {
            sender.sendMessage(ColorUtil.color("&cNo Permission"));
            return true;
        }
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(
                        ColorUtil.color(
                                "&9&lLShop",
                                "&9&l| &f/lenchant <EnchantID> <Level>"
                        ));
                return true;
            }
            if (args.length >= 2) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() == Material.AIR) {
                    player.sendMessage(ColorUtil.color("&cYou can't enchant Air."));
                    return true;
                }
                LEnchant enchant = enchantManager.getEnchant(args[0]);
                if (enchant == null) {
                    player.sendMessage(ColorUtil.color("&aEnchant "+args[0].toUpperCase()+" doesn't exist."));
                    return true;
                }
                int level;
                try {
                    level = Integer.parseInt(args[1]);
                } catch (NumberFormatException ignored) {
                    player.sendMessage(ColorUtil.color("&cNot an Integer."));
                    return true;
                }
                item = enchantManager.addEnchant(item, enchant.getEnchantID(), level);
                player.getInventory().setItemInMainHand(item);
            } else {
                player.sendMessage(ColorUtil.color("&cNot Enough Arguments."));
            }
        }
        return true;
    }
}
