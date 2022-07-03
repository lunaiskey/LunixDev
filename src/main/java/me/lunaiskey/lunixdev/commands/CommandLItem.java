package me.lunaiskey.lunixdev.commands;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.lunixitems.LunixItem;
import me.lunaiskey.lunixdev.lunixitems.LunixItemType;
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

    private Map<LunixItemType, LunixItem> itemMap = LunixDev.getLunixDev().getItemManager().getItemMap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                p.sendMessage(
                        ColorUtil.color("&3&lLItem:",
                        "&3&l| &f/litem <id> [amount] [player]"
                        ));
                return true;
            }
            try {
                LunixItemType type = LunixItemType.valueOf(args[0].toUpperCase());
                ItemStack item = LunixDev.getLunixDev().getItemManager().getItem(type);
                if (item == null || item.getType() == Material.AIR) {
                    p.sendMessage(ColorUtil.color("&cItemID \"+args[0]+\" doesn't have an itemstack attached to it."));
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
                        p.sendMessage(ColorUtil.color("&cPlayer \""+args[2]+"\" isn't online."));
                        return true;
                    }
                }
                item.setAmount(amount);
                otherPlayer.getInventory().addItem(item);
                p.sendMessage(ColorUtil.color("&aYou have given "+otherPlayer.getName()+" &e"+type.name()));
            } catch (Exception ignored) {
                p.sendMessage(ColorUtil.color("&cItemID \""+args[0]+"\" doesn't exist."));
            }
        }
        return true;
    }
}
