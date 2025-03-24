package com.mineplex.studio.essencepvp.attributes;

public class LevelScalingModifier implements AttributeModifier {
    private final int startLevel;
    private final int maxLevel;
    private final double valuePerLevel;
    
    public LevelScalingModifier(int startLevel, int maxLevel, double valuePerLevel) {
        this.startLevel = startLevel;
        this.maxLevel = maxLevel;
        this.valuePerLevel = valuePerLevel;
    }
    
    @Override
    public double apply(double currentValue, int level) {
        int effectiveLevel = Math.min(level, maxLevel) - startLevel + 1;
        int levelsToApply = Math.max(0, effectiveLevel);
        return currentValue + (levelsToApply * valuePerLevel);
    }
    
    @Override
    public boolean appliesAtLevel(int level) {
        return level >= startLevel;
    }
}