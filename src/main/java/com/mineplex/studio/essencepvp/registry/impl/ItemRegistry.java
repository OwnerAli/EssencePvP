package com.mineplex.studio.essencepvp.registry.impl;

import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.armor.diamond.DiamondBoots;
import com.mineplex.studio.essencepvp.items.armor.diamond.DiamondChestplate;
import com.mineplex.studio.essencepvp.items.armor.diamond.DiamondHelmet;
import com.mineplex.studio.essencepvp.items.armor.diamond.DiamondLeggings;
import com.mineplex.studio.essencepvp.items.armor.gold.GoldBoots;
import com.mineplex.studio.essencepvp.items.armor.gold.GoldChestplate;
import com.mineplex.studio.essencepvp.items.armor.gold.GoldHelmet;
import com.mineplex.studio.essencepvp.items.armor.gold.GoldLeggings;
import com.mineplex.studio.essencepvp.items.armor.iron.IronBoots;
import com.mineplex.studio.essencepvp.items.armor.iron.IronChestplate;
import com.mineplex.studio.essencepvp.items.armor.iron.IronHelmet;
import com.mineplex.studio.essencepvp.items.armor.iron.IronLeggings;
import com.mineplex.studio.essencepvp.items.armor.leather.LeatherBoots;
import com.mineplex.studio.essencepvp.items.armor.leather.LeatherChestplate;
import com.mineplex.studio.essencepvp.items.armor.leather.LeatherHelmet;
import com.mineplex.studio.essencepvp.items.armor.leather.LeatherLeggings;
import com.mineplex.studio.essencepvp.items.essence.EssenceItem;
import com.mineplex.studio.essencepvp.items.weapons.impl.*;
import com.mineplex.studio.essencepvp.registry.Registry;

public class ItemRegistry extends Registry<String, CustomItem> {

    public void init() {
        register(new WoodenSword());
        register(new StoneSword());
        register(new GoldSword());
        register(new IronSword());
        register(new DiamondSword());
        register(new LeatherHelmet());
        register(new LeatherChestplate());
        register(new LeatherLeggings());
        register(new LeatherBoots());
        register(new GoldHelmet());
        register(new GoldChestplate());
        register(new GoldLeggings());
        register(new GoldBoots());
        register(new IronHelmet());
        register(new IronChestplate());
        register(new IronLeggings());
        register(new IronBoots());
        register(new DiamondHelmet());
        register(new DiamondChestplate());
        register(new DiamondLeggings());
        register(new DiamondBoots());
        register(new EssenceItem());
    }

    public void register(CustomItem customItem) {
        register(customItem.getId(), customItem);
    }

    //#region Lazy Initialization
    public static ItemRegistry getInstance() {
        return ItemRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final ItemRegistry instance = new ItemRegistry();
    }
    //#endregion

}
