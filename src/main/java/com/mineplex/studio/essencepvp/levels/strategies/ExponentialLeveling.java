package com.mineplex.studio.essencepvp.levels.strategies;

public record ExponentialLeveling(double baseXP, double growthRate) implements LevelingStrategy {

    @Override
    public int getLevelBasedOnXP(double experience) {
        return (int) (Math.log(experience / baseXP) / Math.log(growthRate)) + 1;
    }

    @Override
    public double getXPForNextLevel(int currentLevel) {
        return (int) (baseXP * Math.pow(growthRate, currentLevel));
    }

}
