package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import com.mineplex.studio.essencepvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class IronSword extends CustomWeapon {

    public IronSword() {
        super("iron_sword",
                new ItemBuilder(Material.IRON_SWORD)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .build(),
                "&fIron Sword&7&l", 25,
                5, 30, 100);
    }

    @Override
    protected double getBaseDamage() {
        return 3; // level 1 = 3
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(320, 1.6);
    }

}
