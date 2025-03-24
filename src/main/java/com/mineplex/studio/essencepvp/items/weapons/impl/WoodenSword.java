package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WoodenSword extends CustomWeapon {

    public WoodenSword() {
        super("wooden_sword", new ItemStack(Material.WOODEN_SWORD), "&6Wooden Sword &e&l$level",
                22.5, 5, 30, 3);
    }

    @Override
    protected double getBaseDamage() {
        return 1.8;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(100, 1.4);
    }

}