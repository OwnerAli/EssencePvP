package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import com.mineplex.studio.essencepvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class WoodenSword extends CustomWeapon {

    public WoodenSword() {
        super("wooden_sword",
                new ItemBuilder(Material.WOODEN_SWORD)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .build(),
                "&6Wooden Sword&e&l",
                15, 5, 30, 3);
    }

    @Override
    protected double getBaseDamage() {
        return 2.0;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new ExponentialLeveling(100, 1.4);
    }

}