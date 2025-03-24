package com.mineplex.studio.essencepvp.items.armor.gold;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class GoldChestplate extends CustomArmor {

    public GoldChestplate() {
        super("gold_chestplate", Material.GOLDEN_CHESTPLATE, "&eGold Chestplate &6&l$level",
                6.0, 2, 12, 25.0);
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