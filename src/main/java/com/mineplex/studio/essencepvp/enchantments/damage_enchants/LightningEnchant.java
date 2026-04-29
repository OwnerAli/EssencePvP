package com.mineplex.studio.essencepvp.enchantments.damage_enchants;

import com.mineplex.studio.essencepvp.enchantments.Enchantment;
import com.mineplex.studio.essencepvp.enchantments.modifiers.EnchantLevelModifier;
import com.mineplex.studio.essencepvp.enchantments.triggers.damage_trigger.DamageTrigger;
import com.mineplex.studio.essencepvp.items.CustomItem;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class LightningEnchant extends Enchantment {

    public LightningEnchant() {
        super("lightning", "&f&lLightning", 4,
                new EnchantLevelModifier(Map.of(
                        1, 0.5,
                        2, 0.6,
                        3, 0.7,
                        4, 0.8
                )));
        addTrigger(new DamageTrigger(context -> {
            if (!(getChance().shouldTrigger(context.level()))) return;

            Entity defender = context.defender();

            if (defender == null) return;
            if (!(defender instanceof Damageable damageable)) return;

            defender.getWorld()
                    .strikeLightningEffect(defender.getLocation());

            ItemStack triggerItem = context.triggerItem();
            CustomItem.getCustomWeaponFromBukkitItem(triggerItem)
                    .ifPresent(customWeapon -> {
                        double damage = Math.min(customWeapon.getDamage(triggerItem),
                                damageable.getHealth() - 1);
                        damageable.damage(damage);
                    });
        }));
    }

}