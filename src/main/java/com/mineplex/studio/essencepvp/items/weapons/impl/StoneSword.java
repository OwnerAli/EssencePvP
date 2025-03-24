package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StoneSword extends CustomWeapon {

    public StoneSword() {
        super("stone_sword", new ItemStack(Material.STONE_SWORD), "&7Stone Sword &f&l$level", 25,
                5, 30, 100);
    }

    @Override
    protected double getBaseDamage() {
        return 1.2; // level 1 = 1.2
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(140, 1.4);
    }

}
