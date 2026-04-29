package com.mineplex.studio.essencepvp.items.display_modules.item_lore_modules;

import com.mineplex.studio.essencepvp.items.LevelableItem;
import com.mineplex.studio.essencepvp.items.display_modules.DisplayModule;
import com.mineplex.studio.essencepvp.utils.Chat;
import com.mineplex.studio.essencepvp.utils.FormatUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class XPProgressModule implements DisplayModule {
    private final LevelableItem levelable;

    public XPProgressModule(LevelableItem levelable) {
        this.levelable = levelable;
    }

    @Override
    public void apply(ItemStack itemStack) {
        update(itemStack);
    }

    @Override
    public void remove(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                List<String> strip = Chat.strip(lore);
                for (int i = 0; i < lore.size(); i++) {
                    if (strip.get(i).contains("Essence")) {
                        lore.remove(lore.get(i - 1));
                        lore.remove(lore.get(i)); // Essence text
                        lore.remove(lore.get(i + 1)); // Progress bar
                        lore.remove(lore.get(i + 2)); // Progress counter
                    }
                }
                meta.setLore(lore);
            }
            itemStack.setItemMeta(meta);
        }
    }

    @Override
    public void update(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;

        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<>();
        List<String> strippedLore = Chat.strip(lore);

        String progressBar = generateXPProgressBar(itemStack);
        String xpCounter = getXPProgressCounter(itemStack);

        // Look for existing progress section and replace it
        boolean updated = false;
        for (int i = 0; i < lore.size(); i++) {
            if (strippedLore.get(i).contains("Essence")) {
                lore.set(i + 1, progressBar); // Update progress bar
                lore.set(i + 2, xpCounter);  // Update XP counter
                updated = true;
                break;
            }
        }

        // If no existing progress section was found, append it
        if (!updated) {
            lore.add(" ");
            lore.add(Chat.colorize("&6&lEssence"));
            lore.add(Chat.colorize(progressBar));
            lore.add(Chat.colorize(xpCounter));
            lore.add(" ");
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Calculates the XP progress within the current level.
     *
     * @param itemStack The item whose XP progress should be calculated.
     * @return The percentage progress (e.g., 75%).
     */
    private int getXPProgressForLevel(ItemStack itemStack) {
        double currentXP = levelable.getCurrentXP(itemStack);
        int currentLevel = levelable.getStoredLevel(itemStack);
        double xpForCurrentLevel = levelable.getLevelingStrategy().getXPForNextLevel(currentLevel - 1);
        double xpForNextLevel = levelable.getLevelingStrategy().getXPForNextLevel(currentLevel);

        double progressXP = currentXP - xpForCurrentLevel;
        double neededXP = xpForNextLevel - xpForCurrentLevel;

        return (int) Math.round((progressXP / neededXP) * 100);
    }

    /**
     * Calculates the current XP within the level and the XP needed to reach the next level.
     *
     * @param itemStack The item whose XP should be calculated.
     * @return A formatted string like "XP: 1,234 / 2,500".
     */
    private String getXPProgressCounter(ItemStack itemStack) {
        double currentXP = levelable.getCurrentXP(itemStack);
        int currentLevel = levelable.getStoredLevel(itemStack);
        double xpForCurrentLevel = levelable.getLevelingStrategy().getXPForNextLevel(currentLevel - 1);
        double xpForNextLevel = levelable.getLevelingStrategy().getXPForNextLevel(currentLevel);

        double progressXP = currentXP - xpForCurrentLevel;
        double neededXP = xpForNextLevel - xpForCurrentLevel;

        return String.format(Chat.colorize("&7(&f%s &7/ %s)"), FormatUtils.formatNumber(progressXP),
                FormatUtils.formatNumber(neededXP));
    }

    /**
     * Generates a progress bar for the item's XP within the current level.
     * - The bar has 10 segments.
     * - Overflow XP turns the last segment red.
     * - The percentage is based on the XP progress within the current level.
     *
     * @param itemStack The item whose XP progress should be displayed.
     * @return A formatted string containing the XP progress bar and percentage.
     */
    private String generateXPProgressBar(ItemStack itemStack) {
        int progressPercent = getXPProgressForLevel(itemStack);
        int progressSegments = progressPercent / 10; // 10 total segments
        boolean hasOverflow = progressPercent > 100;

        StringBuilder progressBar = new StringBuilder("§7["); // Gray brackets

        for (int i = 0; i < 10; i++) {
            if (i < progressSegments) {
                if (hasOverflow && i == 9) {
                    progressBar.append("§c■"); // Red for overflow
                } else {
                    progressBar.append("§a■"); // Green for normal progress
                }
            } else {
                progressBar.append("§7■"); // Gray for empty segments
            }
        }

        progressBar.append("§7] ").append(String.format(Chat.colorize("&f%d%%"), progressPercent)); // White percentage

        return progressBar.toString();
    }

}
