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

        ItemStack itemInMainHand = player.getInventory()
                .getItemInMainHand();

        CustomItem.getLevelableItemFromBukkitItem(itemInMainHand)
                .ifPresent(customWeapon -> customWeapon.levelUpOneLevelAtATime(itemInMainHand));

        return true;
    }

}
