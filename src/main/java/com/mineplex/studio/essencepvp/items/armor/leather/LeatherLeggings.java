package com.mineplex.studio.essencepvp.items.armor.leather;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class LeatherLeggings extends CustomArmor {

    public LeatherLeggings() {
        super("leather_leggings", Material.LEATHER_LEGGINGS, "&6Leather Leggings &e&l$level",
                4.0, 2, 10, 18.0);
    }

    @Override
    protected double getBaseDefense() {
        return 0.03; // defense in %
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}