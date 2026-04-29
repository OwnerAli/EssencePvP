package com.mineplex.studio.essencepvp.items.armor.iron;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class IronChestplate extends CustomArmor {

    public IronChestplate() {
        super("iron_chestplate", Material.IRON_CHESTPLATE, "&fIron Chestplate&7&l",
                7.0, 2, 15, 30.0);
    }

    @Override
    protected double getBaseDefense() {
        return 5.0;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}
