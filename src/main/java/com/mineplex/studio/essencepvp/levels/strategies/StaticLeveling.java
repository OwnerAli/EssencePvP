package com.mineplex.studio.essencepvp.levels.strategies;

public class StaticLeveling implements LevelingStrategy {
    private final double xpIncreasePerLevel;

    public StaticLeveling(int xpIncreasePerLevel) {
        this.xpIncreasePerLevel = xpIncreasePerLevel;
    }

    @Override
    public int getLevelBasedOnXP(double experience) {
        return (int) (experience / xpIncreasePerLevel);
    }

    @Override
    public double getXPForNextLevel(int currentLevel) {
        return (currentLevel + 1) * xpIncreasePerLevel;
    }

}
