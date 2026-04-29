package com.mineplex.studio.essencepvp.items.armor.leather;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class LeatherChestplate extends CustomArmor {

    public LeatherChestplate() {
        super("leather_chestplate", Material.LEATHER_CHESTPLATE, "&6Leather Chestplate&e&l",
                5.0, 2, 10, 20.0);
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