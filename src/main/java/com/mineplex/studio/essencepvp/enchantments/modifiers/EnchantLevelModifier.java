package com.mineplex.studio.essencepvp.enchantments.modifiers;

import com.mineplex.studio.essencepvp.Essencepvp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EnchantLevelModifier {
    private final Map<Integer, Double> levelValues;

    public EnchantLevelModifier(Map<Integer, Double> values) {
        this.levelValues = new HashMap<>(values);
    }

    public EnchantLevelModifier() {
        this.levelValues = new HashMap<>();
    }

    /**
     * Add a value for a specific level
     */
    public EnchantLevelModifier addMultiplier(int level, double value) {
        levelValues.put(level, value);
        return this;
    }

    /**
     * Get the value for a specific level
     */
    public double getValue(int level) {
        return levelValues.getOrDefault(level, 0.0);
    }

    /**
     * Check if an event should trigger based on level-based probability
     *
     * @param level Enchantment level
     * @return true if the event should trigger
     */
    public boolean shouldTrigger(int level) {
        double chance = getValue(level);
        return Essencepvp.getInstance().getRandom().nextDouble() < chance;
    }

    /**
     * Calculate a value using a custom formula based on level
     *
     * @param level   Enchantment level
     * @param formula Function that converts level to a value
     * @return The calculated value
     */
    public static double calculate(int level, Function<Integer, Double> formula) {
        return formula.apply(level);
    }

}
