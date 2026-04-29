package com.mineplex.studio.essencepvp.items.armor.gold;

import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;

public class GoldHelmet extends CustomArmor {

    public GoldHelmet() {
        super("gold_helmet", Material.GOLDEN_HELMET, "&eGold Helmet&6&l",
                4.0, 2, 12, 18.0);
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