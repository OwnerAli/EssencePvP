package com.mineplex.studio.essencepvp.items.armor.iron;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class IronHelmet extends CustomArmor {

    public IronHelmet() {
        super("iron_helmet", Material.IRON_HELMET, "&fIron Helmet &7&l$level",
                5.0, 2, 15, 22.0);
    }

    @Override
    protected double getBaseDefense() {
        return 3.0;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}