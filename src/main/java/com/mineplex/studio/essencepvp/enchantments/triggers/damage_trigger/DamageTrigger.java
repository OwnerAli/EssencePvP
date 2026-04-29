package com.mineplex.studio.essencepvp.enchantments.triggers.damage_trigger;

import com.mineplex.studio.essencepvp.enchantments.contexts.EnchantmentContext;
import com.mineplex.studio.essencepvp.enchantments.triggers.EnchantmentTrigger;
import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.function.Consumer;

public class DamageTrigger implements EnchantmentTrigger {
    private final Consumer<EnchantmentContext> onDamageConsumer;

    public DamageTrigger(Consumer<EnchantmentContext> onDamageConsumer) {
        this.onDamageConsumer = onDamageConsumer;
    }

    @Override
    public void trigger(EnchantmentContext context) {
        if (!(context.event() instanceof EntityDamageEvent)) return;
        onDamageConsumer.accept(context);
    }

}
