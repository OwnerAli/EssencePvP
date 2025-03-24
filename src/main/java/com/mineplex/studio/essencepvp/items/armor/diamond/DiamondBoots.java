package com.mineplex.studio.essencepvp.items.armor.diamond;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class DiamondBoots extends CustomArmor {

    public DiamondBoots() {
        super("diamond_boots", Material.DIAMOND_BOOTS, "&bDiamond Boots &f&l$level",
                5.0, 2, 20, 20.0);
    }

    @Override
    protected double getBaseDefense() {
        return 1.5; // percent defense
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}
