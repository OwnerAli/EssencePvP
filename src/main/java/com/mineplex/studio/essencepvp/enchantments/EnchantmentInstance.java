package com.mineplex.studio.essencepvp.enchantments;

import com.mineplex.studio.essencepvp.enchantments.contexts.EnchantmentContext;
import com.mineplex.studio.essencepvp.registry.impl.EnchantRegistry;
import lombok.Getter;

@Getter
public class EnchantmentInstance {
    private final String enchantmentID;
    private int level;

    public EnchantmentInstance(String enchantmentID, int level) {
        this.enchantmentID = enchantmentID;
        this.level = level;
    }

    public void setLevel(int level) {
        EnchantRegistry.getInstance()
                .get(enchantmentID)
                .ifPresent(enchantment ->
                        this.level = Math.min(enchantment.getMaxLevel(), level));
    }

    public void incrementLevel() {
        level++;
    }

    public void trigger(EnchantmentContext enchantmentContext) {
        EnchantRegistry.getInstance()
                .get(enchantmentID)
                .ifPresent(enchantment -> enchantment.trigger(enchantmentContext));
    }

}
