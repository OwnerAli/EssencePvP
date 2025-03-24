package com.mineplex.studio.essencepvp.items.armor.diamond;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class DiamondChestplate extends CustomArmor {

    public DiamondChestplate() {
        super("diamond_chestplate", Material.DIAMOND_CHESTPLATE, "&bDiamond Chestplate &f&l$level",
                8.0, 2, 20, 40.0);
    }

    @Override
    protected double getBaseDefense() {
        return 2; // percent defense
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}