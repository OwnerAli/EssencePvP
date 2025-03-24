package com.mineplex.studio.essencepvp.commands;

import com.mineplex.studio.essencepvp.items.CustomItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LevelUpCommand extends Command {

    public LevelUpCommand() {
        super("levelup");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        int amount;

        if (args.length > 0) {
            amount = Integer.parseInt(args[0]);
        } else {
            amount = 1;
        }

        ItemStack itemInMainHand = player.getInventory()
                .getItemInMainHand();

        CustomItem.getCustomWeaponFromBukkitItem(itemInMainHand)
                .ifPresent(customWeapon -> customWeapon.levelUpOneLevelAtATime(itemInMainHand));

        return true;
    }

}
