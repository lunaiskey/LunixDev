package me.lunaiskey.lunixdev.commands;

import me.lunaiskey.lunixdev.LunixDev;
import me.lunaiskey.lunixdev.lunixshop.Shop;
import me.lunaiskey.lunixdev.lunixshop.ShopManager;
import me.lunaiskey.lunixdev.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandLShop implements CommandExecutor {
    private ShopManager shopManager;

    public CommandLShop() {
        this.shopManager = LunixDev.getLunixDev().getShopManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("lunixdev.admin")) {
                if (args.length == 0) {
                    player.sendMessage(
                            ColorUtil.color(
                                    "&9&lLShop",
                                    "&9&l| &f/lshop <shopID>"
                    ));
                    return true;
                }
                String shopID = args[0];
                Shop shop = shopManager.getShop(shopID);
                if (shop != null) {
                    shopManager.openShop(player,shop);
                    player.sendMessage(ColorUtil.color("&aOpening "+shop.getShopTitle()+"."));
                } else {
                    player.sendMessage(ColorUtil.color("&cShop "+shopID+" doesn't exist."));
                }
            } else {
                player.sendMessage(ColorUtil.color("&cNo Permission"));
            }
        }
        return true;
    }
}
