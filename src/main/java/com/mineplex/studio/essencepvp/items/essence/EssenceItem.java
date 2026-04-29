package com.mineplex.studio.essencepvp.items.essence;

import com.mineplex.studio.essencepvp.items.ActionableItem;
import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.DragAndDropApplicableItem;
import com.mineplex.studio.essencepvp.items.LevelableItem;
import com.mineplex.studio.essencepvp.items.actions.holder.ItemActionHolder;
import com.mineplex.studio.essencepvp.items.actions.impl.PlayerInteractAction;
import com.mineplex.studio.essencepvp.items.display_modules.DisplayModule;
import com.mineplex.studio.essencepvp.items.display_modules.item_name_modules.LevelDisplayModule;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import com.mineplex.studio.essencepvp.levels.strategies.StaticLeveling;
import com.mineplex.studio.essencepvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class EssenceItem extends CustomItem implements LevelableItem, ActionableItem, DragAndDropApplicableItem {

    public EssenceItem() {
        super(
                "essence",
                new ItemBuilder(Material.WIND_CHARGE)
                        .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                        .glowing()
                        .build(),
                "&e&l&6&lEssence"
        );
    }

    @Override
    public ItemStack createBukkitItem() {
        ItemStack bukkitItem = super.createBukkitItem();
        LevelableItem.super.applyLevelableDataToItem(bukkitItem);
        LevelableItem.super.applyXP(bukkitItem, 0);
        return bukkitItem;
    }

    @Override
    public LevelingStrategy getLevelingStrategy() {
        return new StaticLeveling(1);
    }

    @Override
    public ItemActionHolder getActionHolder() {
        return new ItemActionHolder(
                Set.of(
                        new PlayerInteractAction(event -> event.setCancelled(true))));
    }

    @Override
    public Set<DisplayModule> getDisplayModules() {
        return Set.of(
                new LevelDisplayModule(this, true)
        );
    }

}