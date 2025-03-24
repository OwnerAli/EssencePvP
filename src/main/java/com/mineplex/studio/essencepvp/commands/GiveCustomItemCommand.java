package com.mineplex.studio.essencepvp.commands;

import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.LevelableItem;
import com.mineplex.studio.essencepvp.registry.impl.ItemRegistry;
import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class GiveCustomItemCommand extends Command {

    public GiveCustomItemCommand() {
        super("give");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        // Command can only be executed by players
        if (!(sender instanceof Player player)) return false;

        // Show usage message if no arguments are provided
        if (args.length < 1) {
            Chat.tell(player, "&cUsage: /give <item_id> [amount]");
            return false;
        }

        // Get the item ID from the first argument
        String materialName = args[0];
        ItemRegistry itemRegistry = ItemRegistry.getInstance();

        // Handle special case: armor set command
        // Usage: /give <material_name> set
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("set")) {
                // These IDs are defined but not currently used in the command
                // Could be expanded to give full armor sets in the future
                String helmetID = materialName + "_helmet";
                String chestplateID = materialName + "_chestplate";
                String leggingsID = materialName + "_leggings";
                String bootsID = materialName + "_boots";

                // Currently only gives the base material item, not the armor set
                Optional<CustomItem> helmet = itemRegistry.get(helmetID);
                Optional<CustomItem> chestplate = itemRegistry.get(chestplateID);
                Optional<CustomItem> leggings = itemRegistry.get(leggingsID);
                Optional<CustomItem> boots = itemRegistry.get(bootsID);


                helmet.ifPresent(customItem -> player.getInventory()
                        .addItem(customItem.createBukkitItem()));
                chestplate.ifPresent(customItem -> player.getInventory()
                        .addItem(customItem.createBukkitItem()));
                leggings.ifPresent(customItem -> player.getInventory()
                        .addItem(customItem.createBukkitItem()));
                boots.ifPresent(customItem -> player.getInventory()
                        .addItem(customItem.createBukkitItem()));

                return true;
            }
            itemRegistry.get(materialName)
                    .ifPresent(customItem -> {
                        if (!(customItem instanceof LevelableItem levelableItem)) return;
                        ItemStack bukkitItem = customItem.createBukkitItem();
                        levelableItem.applyXPAndAutoLevelUp(bukkitItem,
                                Double.parseDouble(args[1]));
                        player.getInventory()
                                .addItem(bukkitItem);
                    });
            return true;
        }

        // Default behavior: give the single specified item to the player
        // Usage: /give <item_id>
        itemRegistry
                .get(materialName)
                .ifPresent(customItem -> player.getInventory()
                        .addItem(customItem.createBukkitItem()));
        return true;
    }

}
