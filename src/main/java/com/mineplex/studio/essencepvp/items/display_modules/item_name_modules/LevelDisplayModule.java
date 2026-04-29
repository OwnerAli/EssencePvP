package com.mineplex.studio.essencepvp.items.display_modules.item_name_modules;

import com.mineplex.studio.essencepvp.items.LevelableItem;
import com.mineplex.studio.essencepvp.items.display_modules.DisplayModule;
import com.mineplex.studio.essencepvp.utils.Chat;
import com.mineplex.studio.essencepvp.utils.FormatUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LevelDisplayModule implements DisplayModule {
    private final LevelableItem levelable;
    private final boolean displayInFront;

    public LevelDisplayModule(LevelableItem levelable, boolean displayInFront) {
        this.levelable = levelable;
        this.displayInFront = displayInFront;
    }

    @Override
    public void apply(ItemStack itemStack) {
        update(itemStack);
    }

    @Override
    public void remove(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(getBaseItemName(meta)); // Restore base name without level
            itemStack.setItemMeta(meta);
        }
    }

    @Override
    public void update(ItemStack itemStack) {
        int level = levelable.getStoredLevel(itemStack);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            String baseName = getBaseItemName(meta);
            if (displayInFront) {
                Chat.log(baseName);
                String colorPrefix = baseName.substring(0, 2);
                meta.setDisplayName(Chat.colorize(colorPrefix + FormatUtils.formatNumber(level) + " " + baseName));
            } else {
                meta.setDisplayName(Chat.colorize(baseName + " " + level));
            }
            itemStack.setItemMeta(meta);
        }
    }

    /**
     * Extracts the base item name by removing any previous level suffix.
     */
    private String getBaseItemName(ItemMeta meta) {
        if (meta.hasDisplayName()) {
            if (displayInFront) {
                String colorCode = meta.getDisplayName().substring(0, 2);
                return colorCode + meta.getDisplayName().replaceAll("^" + colorCode + "\\d+\\s+", "");
            }
            return meta.getDisplayName()
                    .replaceAll("\\s+\\d+$", ""); // Remove existing number at the end
        }
        return formatDefaultItemName(meta);
    }

    /**
     * Returns a clean name for the item type.
     */
    private String formatDefaultItemName(ItemMeta meta) {
        return WordUtils.capitalizeFully(meta.getDisplayName().replace("_", " ")); // Capitalize properly
    }

}