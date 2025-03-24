package com.mineplex.studio.essencepvp.attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class AttributeSystem {
    // Base attributes
    private final Map<AttributeType, Double> baseAttributes = new HashMap<>();
    
    // Attribute modifiers that can change how attributes scale
    private final Map<AttributeType, List<AttributeModifier>> attributeModifiers = new HashMap<>();
    
    public AttributeSystem() {
        // Initialize default attributes
        for (AttributeType type : AttributeType.values()) {
            baseAttributes.put(type, 0.0);
            attributeModifiers.put(type, new ArrayList<>());
        }
    }
    
    public AttributeSystem setBaseAttribute(AttributeType type, double value) {
        baseAttributes.put(type, value);
        return this;
    }
    
    public double getBaseAttribute(AttributeType type) {
        return baseAttributes.getOrDefault(type, 0.0);
    }
    
    public AttributeSystem addModifier(AttributeType type, AttributeModifier modifier) {
        attributeModifiers.get(type).add(modifier);
        return this;
    }
    
    public double calculateAttribute(AttributeType type, int level) {
        double value = getBaseAttribute(type);
        
        // Apply all modifiers for this attribute type
        for (AttributeModifier modifier : attributeModifiers.get(type)) {
            if (modifier.appliesAtLevel(level)) {
                value = modifier.apply(value, level);
            }
        }
        
        return value;
    }
    
    public enum AttributeType {
        DAMAGE,
        DEFENSE,
        STRENGTH,
        SPEED,
        HEALTH,
        CRITICAL_CHANCE,
        CRITICAL_DAMAGE
        // Add more as needed
    }

}