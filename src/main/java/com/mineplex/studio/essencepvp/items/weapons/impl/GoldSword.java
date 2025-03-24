package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GoldSword extends CustomWeapon {

    public GoldSword() {
        super("gold_sword", new ItemStack(Material.GOLDEN_SWORD), "&eGold Sword &6&l$level", 32.5,
                5, 30, 100);
    }

    @Override
    protected double getBaseDamage() {
        return 1.5; // level 1 = 1.5
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(200, 1.6);
    }

}
