package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class IronSword extends CustomWeapon {

    public IronSword() {
        super("iron_sword", new ItemStack(Material.IRON_SWORD), "&fIron Sword &7&l$level", 25,
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
