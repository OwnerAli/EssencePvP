package com.mineplex.studio.essencepvp.items.armor;

import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.DragAndDropReceivableItem;
import com.mineplex.studio.essencepvp.items.LevelableItem;
import com.mineplex.studio.essencepvp.levels.strategies.ExponentialLeveling;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public abstract class CustomArmor extends CustomItem implements LevelableItem, DragAndDropReceivableItem {
    protected final double defenseIncreasePerInterval;
    protected final int defenseIncreaseInterval;
    protected final int levelDefenseIncreaseCap;
    protected final double defenseCap;

    protected CustomArmor(String id, Material itemType, String displayName, double defenseIncreasePerInterval, int defenseIncreaseInterval,
                          int levelDefenseIncreaseCap, double defenseCap) {
        super(id, new ItemStack(itemType), displayName);
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

    protected double calculateDefense(int level) {
        int levelMultiplier = Math.min(level, levelDefenseIncreaseCap);
        double multiplier = 1 + defenseIncreasePerInterval / 100;
        int power = levelMultiplier / defenseIncreaseInterval;

        return Math.min(Math.pow(multiplier, power) * getBaseDefense(), defenseCap);
    }

    @Override
    public ItemStack createBukkitItem() {
        ItemStack bukkitItem = super.createBukkitItem();
        LevelableItem.super.applyXP(bukkitItem, getLevelingStrategy() instanceof ExponentialLeveling exponentialLeveling ?
                exponentialLeveling.baseXP() : 0);
        updateLevelDisplay(bukkitItem);
        return bukkitItem;
    }

    @Override
    public void applyPlaceholders() {
        // TODO: IMPLEMENT
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