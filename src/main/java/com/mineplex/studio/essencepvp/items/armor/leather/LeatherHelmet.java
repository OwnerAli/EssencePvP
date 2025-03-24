package com.mineplex.studio.essencepvp.items.armor.leather;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class LeatherHelmet extends CustomArmor {

    public LeatherHelmet() {
        super("leather_helmet", Material.LEATHER_HELMET, "&6Leather Helmet &e&l$level",
                5.0, 2, 10, 20.0);
    }

    @Override
    protected double getBaseDefense() {
        return 0.025; // defense in %
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}