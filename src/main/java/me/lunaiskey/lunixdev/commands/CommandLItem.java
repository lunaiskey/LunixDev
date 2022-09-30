package me.lunaiskey.lunixdev.commands;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.lunixitems.ItemManager;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CommandLItem implements CommandExecutor {
    private ItemManager itemManager = LunixDev.getLunixDev().getItemManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("lunixdev.admin")) {
                if (args.length == 0) {
                    p.sendMessage(
                            ColorUtil.color("&3&lLItem:",
                                    "&3&l| &f/litem <id> [amount] [player]",
                                    "&e&l| &f/litem updatehand"
                            ));
                    return true;
                }
                if (args[0].equalsIgnoreCase("updatehand")) {
                    ItemStack item = itemManager.updateItemStack(p.getInventory().getItemInMainHand());
                    p.getInventory().setItemInMainHand(item);
                    p.sendMessage(ColorUtil.color("&aUpdated Main Hand item."));
                    return true;
                }
                String itemID = args[0].toUpperCase();
                try {
                    ItemStack item = LunixDev.getLunixDev().getItemManager().getLunixItemStack(itemID);
                    if (item == null || item.getType() == Material.AIR) {
                        p.sendMessage(ColorUtil.color("&cItem \""+args[0]+"\" doesn't exist."));
                        return true;
                    }
                    Player otherPlayer = p;
                    int amount = 1;
                    if (args.length >= 2) {
                        try {
                            amount = Integer.parseInt(args[1]);
                            if (amount < 0) {
                                p.sendMessage(ColorUtil.color("&cAmount has to be more then 0."));
                                return true;
                            }
                            item.setAmount(amount);
                        } catch (Exception ignored) {
                            p.sendMessage(ColorUtil.color("&cInvalid amount."));
                            return true;
                        }
                    }
                    if (args.length >= 3) {
                        otherPlayer = Bukkit.getPlayer(args[2]);
                        if (otherPlayer == null) {
                            p.sendMessage(ColorUtil.color("&cPlayer \"" + args[2] + "\" isn't online."));
                            return true;
                        }
                    }
                    item.setAmount(amount);
                    otherPlayer.getInventory().addItem(item);
                    p.sendMessage(ColorUtil.color("&aYou have given " + otherPlayer.getName() + " &e" + itemID));
                } catch (Exception ignored) {
                    p.sendMessage(ColorUtil.color("&cItemID \"" + args[0] + "\" doesn't exist."));
                }
            } else {
                p.sendMessage(ColorUtil.color("&cNo Permission!"));
            }
        } else {
            sender.sendMessage(ColorUtil.color("&cPlayer only command!"));
        }
        return true;
    }
}
