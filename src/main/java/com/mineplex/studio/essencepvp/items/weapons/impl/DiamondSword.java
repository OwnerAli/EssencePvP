package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import com.mineplex.studio.essencepvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class DiamondSword extends CustomWeapon {

    public DiamondSword() {
        super("diamond_sword",
                new ItemBuilder(Material.DIAMOND_SWORD)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .build(),
                "&bDiamond Sword&f&l", 15,
                5, 30, 100);
    }

    @Override
    protected double getBaseDamage() {
        return 7; // level 1 = 7
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(500, 1.9);
    }

}