package com.mineplex.studio.essencepvp.items.weapons.impl;

import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import com.mineplex.studio.essencepvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class StoneSword extends CustomWeapon {

    public StoneSword() {
        super("stone_sword",
                new ItemBuilder(Material.STONE_SWORD)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .build(),
                "&7Stone Sword&f&l", 25,
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
