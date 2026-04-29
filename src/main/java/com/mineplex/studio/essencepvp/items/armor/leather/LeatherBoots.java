package com.mineplex.studio.essencepvp.items.armor.leather;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class LeatherBoots extends CustomArmor {

    public LeatherBoots() {
        super("leather_boots", Material.LEATHER_BOOTS, "&6Leather Boots&e&l",
                2.0, 2, 10, 10.0);
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