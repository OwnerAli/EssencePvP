package com.mineplex.studio.essencepvp.commands;

import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantCommand extends Command {

    public EnchantCommand() {
        super("enchant");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length < 1) {
            Chat.tell(player, "&cUsage: /enchant [enchant-id] [level]");
            return false;
        }
        String enchantID = args[0];

        ItemStack itemInMainHand = player.getInventory()
                .getItemInMainHand();

        CustomItem.getEnchantableItemFromBukkitItem(itemInMainHand)
                .ifPresent(customWeapon -> customWeapon.addEnchantmentOrUpdateLevel(itemInMainHand,
                        enchantID, Integer.parseInt(args[1])));

        return true;
    }

}
