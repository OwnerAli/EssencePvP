package com.mineplex.studio.essencepvp.items.display_modules;

import org.bukkit.inventory.ItemStack;

public interface DisplayModule {

    void apply(ItemStack itemStack);

    void remove(ItemStack itemStack);

    void update(ItemStack itemStack);

}
