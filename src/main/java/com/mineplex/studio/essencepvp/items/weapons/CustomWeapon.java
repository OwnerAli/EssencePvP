package com.mineplex.studio.essencepvp.items.weapons;

import com.mineplex.studio.essencepvp.items.ActionableItem;
import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.DragAndDropReceivableItem;
import com.mineplex.studio.essencepvp.items.LevelableItem;
import com.mineplex.studio.essencepvp.items.actions.holder.ItemActionHolder;
import com.mineplex.studio.essencepvp.items.actions.impl.InventoryClickAction;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public abstract class CustomWeapon extends CustomItem implements LevelableItem, ActionableItem, DragAndDropReceivableItem {
    protected final double damageIncreasePerInterval;
    protected final int damageIncreaseInterval;
    protected final int levelDamageIncreaseCap;
    protected final double damageCap;

    protected CustomWeapon(String id, ItemStack item, String displayName, double damageIncreasePerInterval,
                           int damageIncreaseInterval, int levelDamageIncreaseCap, double damageCap) {
        super(id, item, displayName);
        this.damageIncreasePerInterval = damageIncreasePerInterval;
        this.damageIncreaseInterval = damageIncreaseInterval;
        this.levelDamageIncreaseCap = levelDamageIncreaseCap;
        this.damageCap = damageCap;
    }

    public double getDamage(ItemStack itemStack) {
        return calculateDamage(getLevel(itemStack));
    }

    protected double calculateDamage(int level) {
        int levelMultiplier = Math.min(level, levelDamageIncreaseCap);
        double multiplier = 1 + damageIncreasePerInterval / 100;
        int power = levelMultiplier / damageIncreaseInterval;

        return Math.min(Math.pow(multiplier, power) * getBaseDamage(), damageCap);
    }

    @Override
    public ItemStack createBukkitItem() {
        ItemStack bukkitItem = super.createBukkitItem();
        LevelableItem.super.applyLevelableDataToItem(bukkitItem);
        LevelableItem.super.applyXPAndAutoLevelUp(bukkitItem, getLevelingStrategy() instanceof ExponentialLeveling exponentialLeveling ?
                exponentialLeveling.baseXP() : 0);
        updateLevelDisplay(bukkitItem);
        return bukkitItem;
    }

    @Override
    public void applyPlaceholders() {
        // TODO: IMPLEMENT
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
     * @return base damage in HP (e.g 1 = 1/2 heart of damage)
     */
    protected abstract double getBaseDamage();

}
