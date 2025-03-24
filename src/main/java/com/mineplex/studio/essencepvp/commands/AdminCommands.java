package com.mineplex.studio.essencepvp.commands;

import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdminCommands extends Command {

    public AdminCommands() {
        super("a");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length < 1) {
            return false;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "gmc" -> {
                // Set player's gamemode to creative
                player.setGameMode(org.bukkit.GameMode.CREATIVE);
                // Let player know their gamemode was changed
                Chat.tell(player, "&aGamemode set to creative");
                return true;
            }
            case "gms" -> {
                // Set player's gamemode to survival
                player.setGameMode(GameMode.SURVIVAL);
                // Let player know their gamemode was changed
                Chat.tell(player, "&aGamemode set to survival");
                return true;
            }
            case "fly" -> {
                boolean allowFlight = player.getAllowFlight();
                Chat.tell(player, allowFlight ? "&cFlying disabled" : "&aFlying enabled");
                player.setAllowFlight(!allowFlight);
                return true;
            }
            case "feed" -> {
                player.setSaturation(20);
                player.setFoodLevel(20);
                Chat.tell(player, "&aYou have been sated");
                return true;
            }
            case "heal" -> {
                player.sendHealthUpdate(player.getAttribute(Attribute.MAX_HEALTH).getBaseValue(), 20, 20);
                player.setHealth(20);
                Chat.tell(player, "&aYou have been healed");
            }
        }

        return false;
    }

}
