package com.mineplex.studio.essencepvp.registry.impl;

import com.mineplex.studio.essencepvp.enchantments.Enchantment;
import com.mineplex.studio.essencepvp.enchantments.damage_enchants.LightningEnchant;
import com.mineplex.studio.essencepvp.enchantments.damage_enchants.SiphonEnchant;
import com.mineplex.studio.essencepvp.registry.Registry;

public class EnchantRegistry extends Registry<String, Enchantment> {

    public void init() {
        new LightningEnchant().register();
        new SiphonEnchant().register();
    }

    //#region Lazy Initialization
    public static EnchantRegistry getInstance() {
        return EnchantRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final EnchantRegistry instance = new EnchantRegistry();
    }
    //#endregion

}
