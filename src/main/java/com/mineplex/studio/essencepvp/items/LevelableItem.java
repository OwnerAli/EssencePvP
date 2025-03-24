package com.mineplex.studio.essencepvp.items;

import com.mineplex.studio.essencepvp.Essencepvp;
import com.mineplex.studio.essencepvp.levels.strategies.LevelingStrategy;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Interface for items that can gain levels and have attributes that scale with levels
 */
public interface LevelableItem {
    NamespacedKey XP_KEY = new NamespacedKey(Essencepvp.getInstance(), "xp");
    NamespacedKey LEVEL_KEY = new NamespacedKey(Essencepvp.getInstance(), "level");

    LevelingStrategy getLevelingStrategy();

    /**
     * Get the currently displayed level of the item (locked until upgraded).
     */
    default int getLevel(ItemStack itemStack) {
        return getStoredLevel(itemStack);
    }

    /**
     * Get the real level based on total XP (not shown to players until upgraded).
     */
    default int getRealLevel(ItemStack itemStack) {
        Double totalXP = getXPFromItem(itemStack);
        if (totalXP == null) return 1;

        return getLevelingStrategy().getLevelBasedOnXP(totalXP);
    }

    /**
     * Apply XP to the item (does NOT auto-level up).
     */
    default void applyXP(ItemStack itemStack, double amount) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(XP_KEY, PersistentDataType.DOUBLE, amount);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Apply XP to the item and automatically level it up if the XP exceeds the threshold for the next level.
     * If the item is an Essence item, no level-up will occur, only the XP is applied.
     *
     * @param itemStack The item to apply XP to.
     * @param amount    The amount of XP to apply.
     */
    default void applyXPAndAutoLevelUp(ItemStack itemStack, double amount) {
        // If it's a levelable item, apply XP and auto-level it up if needed
        double currentXP = getCurrentXP(itemStack);
        double newXP = currentXP + amount;

        // Apply the XP to the item
        applyXP(itemStack, newXP);

        // Level up the item if the XP exceeds the required threshold for the next level
        levelUpItem(itemStack);
    }

    /**
     * Increment XP, allowing overflow, but NOT changing the displayed level.
     */
    default void incrementXP(ItemStack itemStack, double amount) {
        double currentXP = getCurrentXP(itemStack);
        double newXP = currentXP + amount; // Allow XP to go beyond current level

        applyXP(itemStack, newXP);
    }

    /**
     * Level up the item manually (called when a player runs the level-up command).
     */
    default void levelUpItem(ItemStack itemStack) {
        int currentLevel = getStoredLevel(itemStack);
        int realLevel = getRealLevel(itemStack);

        // If the real level is higher than the stored level, upgrade
        if (realLevel > currentLevel) {
            setStoredLevel(itemStack, realLevel);
            CustomItem.updateLevelDisplay(itemStack);
        }
    }

    /**
     * Level up the item by one level. Even if the item has overflow XP, it will only level up once.
     * This method should be called manually by the player to upgrade the item one level at a time.
     *
     * @param itemStack The item to level up.
     */
    default void levelUpOneLevelAtATime(ItemStack itemStack) {
        int currentLevel = getStoredLevel(itemStack); // Get the stored level
        int realLevel = getRealLevel(itemStack); // Get the real level based on the XP

        // Only level up if the real level is greater than the current stored level
        if (realLevel > currentLevel) {
            // Set the stored level to the real level (level up by one level)
            setStoredLevel(itemStack, currentLevel + 1);
            CustomItem.updateLevelDisplay(itemStack); // Update the item display
        }
    }

    /**
     * Get the XP required to reach the next level.
     */
    default double getXPThreshold(ItemStack itemStack) {
        int currentLevel = getStoredLevel(itemStack);
        return getLevelingStrategy().getXPForNextLevel(currentLevel);
    }

    /**
     * Check if the item's XP has reached or exceeded the threshold for the next level.
     */
    default boolean isFullOfXP(ItemStack itemStack) {
        return getCurrentXP(itemStack) >= getXPThreshold(itemStack);
    }

    /**
     * Get the stored (displayed) level of the item.
     */
    private int getStoredLevel(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(LEVEL_KEY, PersistentDataType.INTEGER, 1);
    }

    /**
     * Set the stored (displayed) level of the item.
     */
    private void setStoredLevel(ItemStack itemStack, int level) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(LEVEL_KEY, PersistentDataType.INTEGER, level);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Get the current XP stored on the item.
     */
    default double getCurrentXP(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(XP_KEY, PersistentDataType.DOUBLE, 0.0);
    }

    /**
     * Ensures that the item has all necessary level-related data stored in its PersistentDataContainer.
     * If the XP or Level keys are missing, they will be initialized with default values.
     *
     * @param itemStack The item to apply levelable data to.
     */
    default void applyLevelableDataToItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

        // Ensure XP key exists, default to 0 if missing
        if (!pdc.has(XP_KEY, PersistentDataType.DOUBLE)) {
            pdc.set(XP_KEY, PersistentDataType.DOUBLE, 0.0);
        }

        // Ensure Level key exists, default to 1 if missing
        if (!pdc.has(LEVEL_KEY, PersistentDataType.INTEGER)) {
            pdc.set(LEVEL_KEY, PersistentDataType.INTEGER, 1);
        }

        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Fetch XP stored in the item.
     */
    private Double getXPFromItem(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().get(XP_KEY, PersistentDataType.DOUBLE);
    }

}