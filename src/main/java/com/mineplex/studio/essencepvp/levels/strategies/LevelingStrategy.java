package com.mineplex.studio.essencepvp.levels.strategies;

public interface LevelingStrategy {

    /**
     * Calculates the level based on experience points.
     *
     * @param experience the experience points
     * @return the corresponding level
     */
    int getLevelBasedOnXP(double experience);

    /**
     * Determines the experience required for the next level.
     *
     * @param currentLevel the current level
     * @return the required experience for the next level
     */
    double getXPForNextLevel(int currentLevel);

}