package com.mineplex.studio.essencepvp.enchantments;

import com.mineplex.studio.essencepvp.enchantments.contexts.EnchantmentContext;
import com.mineplex.studio.essencepvp.enchantments.modifiers.EnchantLevelModifier;
import com.mineplex.studio.essencepvp.enchantments.triggers.EnchantmentTrigger;
import com.mineplex.studio.essencepvp.registry.impl.EnchantRegistry;
import com.mineplex.studio.essencepvp.utils.Chat;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public abstract class Enchantment {
    private final String id;
    private final String displayName;
    private final int maxLevel;
    private final EnchantLevelModifier chance;
    private final Set<EnchantmentTrigger> triggers;

    protected Enchantment(String id, String displayName, int maxLevel, EnchantLevelModifier chance, EnchantmentTrigger... triggers) {
        this.id = id;
        this.displayName = Chat.colorize(displayName);
        this.maxLevel = maxLevel;
        this.chance = chance;
        this.triggers = new HashSet<>(Set.of(triggers));
    }

    public void trigger(EnchantmentContext context) {
        if (!(chance.shouldTrigger(context.level()))) return;
        triggers.forEach(trigger -> trigger.trigger(context));
    }

    public void register() {
        EnchantRegistry.getInstance()
                .register(id, this);
    }

    protected void addTrigger(EnchantmentTrigger trigger) {
        triggers.add(trigger);
    }

}
