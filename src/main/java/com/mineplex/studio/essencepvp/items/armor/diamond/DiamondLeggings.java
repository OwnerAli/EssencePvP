package com.mineplex.studio.essencepvp.items.armor.diamond;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class DiamondLeggings extends CustomArmor {

    public DiamondLeggings() {
        super("diamond_leggings", Material.DIAMOND_LEGGINGS, "&bDiamond Leggings &f&l$level",
                7.0, 2, 20, 34.0);
    }

    @Override
    protected double getBaseDefense() {
        return 2; // percent defense
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}