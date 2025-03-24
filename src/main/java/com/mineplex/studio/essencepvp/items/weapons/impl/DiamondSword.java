package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DiamondSword extends CustomWeapon {

    public DiamondSword() {
        super("diamond_sword", new ItemStack(Material.DIAMOND_SWORD), "&bDiamond Sword &f&l$level", 15,
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