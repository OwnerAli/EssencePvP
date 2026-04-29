package com.mineplex.studio.essencepvp.registry.impl;

import com.mineplex.studio.essencepvp.loot.Loot;
import com.mineplex.studio.essencepvp.loot.impl.ItemXPLoot;
import com.mineplex.studio.essencepvp.registry.Registry;

public class LootRegistry extends Registry<String, Loot> {

    public void init() {
        register("default_item_xp", new ItemXPLoot(15,
                1.5,
                0.75));
    }

    //#region Lazy Initialization
    public static LootRegistry getInstance() {
        return LootRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final LootRegistry instance = new LootRegistry();
    }
    //#endregion

}
