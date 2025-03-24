package com.mineplex.studio.essencepvp.items.armor.iron;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class IronLeggings extends CustomArmor {

    public IronLeggings() {
        super("iron_leggings", Material.IRON_LEGGINGS, "&fIron Leggings &7&l$level",
                6.0, 2, 15, 26.0);
    }

    @Override
    protected double getBaseDefense() {
        return 4.0;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}