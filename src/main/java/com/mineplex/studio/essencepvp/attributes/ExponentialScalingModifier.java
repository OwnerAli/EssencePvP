package com.mineplex.studio.essencepvp.attributes;

public class ExponentialScalingModifier implements AttributeModifier {
    private final int startLevel;
    private final double base;
    private final double coefficient;
    
    public ExponentialScalingModifier(int startLevel, double base, double coefficient) {
        this.startLevel = startLevel;
        this.base = base;
        this.coefficient = coefficient;
    }
    
    @Override
    public double apply(double currentValue, int level) {
        int effectiveLevel = level - startLevel + 1;
        if (effectiveLevel <= 0) return currentValue;
        
        return currentValue + (coefficient * Math.pow(base, effectiveLevel));
    }
    
    @Override
    public boolean appliesAtLevel(int level) {
        return level >= startLevel;
    }
}