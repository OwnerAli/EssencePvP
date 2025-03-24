package com.mineplex.studio.essencepvp.items.armor.gold;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class GoldBoots extends CustomArmor {

    public GoldBoots() {
        super("gold_boots", Material.GOLDEN_BOOTS, "&eGold Boots &6&l$level",
                3.0, 2, 12, 12.0);
    }

    @Override
    protected double getBaseDefense() {
        return 1.0;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}