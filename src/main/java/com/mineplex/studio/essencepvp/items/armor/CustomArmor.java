package com.mineplex.studio.essencepvp.items.armor;

import com.mineplex.studio.essencepvp.items.ActionableItem;
import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.DragAndDropReceivableItem;
import com.mineplex.studio.essencepvp.items.LevelableItem;
import com.mineplex.studio.essencepvp.items.actions.holder.ItemActionHolder;
import com.mineplex.studio.essencepvp.items.actions.impl.InventoryClickAction;
import com.mineplex.studio.essencepvp.items.display_modules.DisplayModule;
import com.mineplex.studio.essencepvp.items.display_modules.item_lore_modules.ArmorStatsModule;
import com.mineplex.studio.essencepvp.items.display_modules.item_lore_modules.XPProgressModule;
import com.mineplex.studio.essencepvp.items.display_modules.item_name_modules.LevelDisplayModule;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import com.mineplex.studio.essencepvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.Set;

public abstract class CustomArmor extends CustomItem implements LevelableItem, ActionableItem, DragAndDropReceivableItem {
    protected final double defenseIncreasePerInterval;
    protected final int defenseIncreaseInterval;
    protected final int levelDefenseIncreaseCap;
    protected final double defenseCap;

    protected CustomArmor(String id, Material itemType, String displayName, double defenseIncreasePerInterval, int defenseIncreaseInterval,
                          int levelDefenseIncreaseCap, double defenseCap) {
        super(id, new ItemBuilder(itemType)
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build(), displayName);
        this.defenseIncreasePerInterval = defenseIncreasePerInterval;
        this.defenseIncreaseInterval = defenseIncreaseInterval;
        this.levelDefenseIncreaseCap = levelDefenseIncreaseCap;
        this.defenseCap = defenseCap;
    }

    /**
     * Calculates the total defense value for one or more armor pieces.
     *
     * @param itemStack One or more armor ItemStacks to calculate defense for
     * @return The combined defense value of all provided armor pieces
     */
    public static double getDefense(ItemStack... itemStack) {
        double totalDefense = 0;
        for (ItemStack bukkitItem : itemStack) {
            if (bukkitItem == null) continue;
            Optional<CustomArmor> customArmorOptional = getCustomArmorFromBukkitItem(bukkitItem);
            if (customArmorOptional.isEmpty()) continue;

            CustomArmor customArmor = customArmorOptional.get();
            totalDefense += customArmor.calculateDefense(customArmor.getLevel(bukkitItem));
        }
        return totalDefense;
    }

    public double calculateDefense(int level) {
        int levelMultiplier = Math.min(level, levelDefenseIncreaseCap);
        double multiplier = 1 + defenseIncreasePerInterval / 100;
        int power = levelMultiplier / defenseIncreaseInterval;

        return Math.min(Math.pow(multiplier, power) * getBaseDefense(), defenseCap);
    }

    @Override
    public ItemStack createBukkitItem() {
        ItemStack bukkitItem = super.createBukkitItem();
        LevelableItem.super.applyLevelableDataToItem(bukkitItem);
        LevelableItem.super.applyXPAndAutoLevelUp(bukkitItem, getLevelingStrategy() instanceof ExponentialLeveling exponentialLeveling ?
                exponentialLeveling.baseXP() : 0);
        return bukkitItem;
    }

    @Override
    public ItemActionHolder getActionHolder() {
        return new ItemActionHolder(
                Set.of(
                        new InventoryClickAction(click -> {
                            ItemStack cursor = click.getCursor();

                            if (cursor.getType() == Material.AIR) return;
                            if (!cursor.hasItemMeta()) return;
                            this.receiveDragAndDrop(click);
                        })
                )
        );
    }

    @Override
    public Set<DisplayModule> getDisplayModules() {
        return Set.of(
                new LevelDisplayModule(this, false),
                new XPProgressModule(this),
                new ArmorStatsModule(this)
        );
    }

    @Override
    public void receiveDragAndDrop(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        ItemStack receivable = event.getCurrentItem();
        ItemStack applicable = event.getCursor();

        CustomItem.getCustomItemFromBukkitItem(applicable)
                .ifPresent(customItem -> {
                    if (customItem instanceof LevelableItem levelableItem) {
                        double currentXP = levelableItem.getCurrentXP(applicable);
                        LevelableItem.super.incrementXP(receivable, currentXP);

                        DragAndDropReceivableItem.super.receiveDragAndDrop(event);
                    }
                });
    }

    /**
     * @return base defense value
     */
    protected abstract double getBaseDefense();

}