package com.mineplex.studio.essencepvp.items.armor.diamond;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class DiamondHelmet extends CustomArmor {

    public DiamondHelmet() {
        super("diamond_helmet", Material.DIAMOND_HELMET, "&bDiamond Helmet &f&l$level",
                6.0, 2, 20, 28.0);
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