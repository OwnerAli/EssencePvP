package com.mineplex.studio.essencepvp.items.armor.gold;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class GoldLeggings extends CustomArmor {

    public GoldLeggings() {
        super("gold_leggings", Material.GOLDEN_LEGGINGS, "&eGold Leggings &6&l$level",
                5.0, 2, 12, 22.0);
    }

    @Override
    protected double getBaseDefense() {
        return 2.5;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}