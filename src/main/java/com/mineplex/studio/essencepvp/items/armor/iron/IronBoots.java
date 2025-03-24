package com.mineplex.studio.essencepvp.items.armor.iron;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class IronBoots extends CustomArmor {

    public IronBoots() {
        super("iron_boots", Material.IRON_BOOTS, "&fIron Boots &7&l$level",
                4.0, 2, 15, 15.0);
    }

    @Override
    protected double getBaseDefense() {
        return 2.0;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}