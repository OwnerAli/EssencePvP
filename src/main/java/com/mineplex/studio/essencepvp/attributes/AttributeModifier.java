package com.mineplex.studio.essencepvp.attributes;

/**
 * Represents a modifier that can be applied to an attribute calculation
 */
public interface AttributeModifier {

    /**
     * Apply this modifier to the current attribute value
     * 
     * @param currentValue The current value of the attribute
     * @param level The level of the entity/item
     * @return The modified attribute value
     */
    double apply(double currentValue, int level);
    
    /**
     * Check if this modifier should be applied at the given level
     * 
     * @param level The level to check
     * @return true if this modifier applies at the given level
     */
    boolean appliesAtLevel(int level);

}