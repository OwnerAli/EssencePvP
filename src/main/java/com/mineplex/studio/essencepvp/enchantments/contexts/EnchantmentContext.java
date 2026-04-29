package com.mineplex.studio.essencepvp.enchantments.contexts;

import com.mineplex.studio.essencepvp.items.CustomItem;
import lombok.Builder;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Builder
public record EnchantmentContext(ItemStack triggerItem, int level, Player attacker, Entity defender, Event event) {
}