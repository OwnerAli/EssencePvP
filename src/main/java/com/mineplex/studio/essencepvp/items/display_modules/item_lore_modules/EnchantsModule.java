package com.mineplex.studio.essencepvp.items.display_modules.item_lore_modules;

import com.mineplex.studio.essencepvp.enchantments.Enchantment;
import com.mineplex.studio.essencepvp.enchantments.EnchantmentInstance;
import com.mineplex.studio.essencepvp.items.EnchantableItem;
import com.mineplex.studio.essencepvp.items.display_modules.DisplayModule;
import com.mineplex.studio.essencepvp.registry.impl.EnchantRegistry;
import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnchantsModule implements DisplayModule {
    private final EnchantableItem enchantableItem;

    public EnchantsModule(EnchantableItem enchantableItem) {
        this.enchantableItem = enchantableItem;
    }

    @Override
    public void apply(ItemStack itemStack) {
        update(itemStack);
    }

    @Override
    public void remove(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;

        List<String> lore = meta.getLore();
        if (lore != null) {
            List<String> strippedLore = Chat.strip(lore);
            lore.removeIf(line -> strippedLore.stream().anyMatch(e -> e.startsWith("Enchantments") || e.contains("•")));
        }

        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    @Override
    public void update(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;

        List<String> lore = itemMeta.getLore();
        if (lore == null) lore = new ArrayList<>();
        List<String> strippedLore = Chat.strip(lore);

        List<EnchantmentInstance> enchantments = enchantableItem.getEnchantments(itemStack);
        EnchantRegistry enchantRegistry = EnchantRegistry.getInstance();
        int enchantModuleIndex = -1;

        // Check if enchantments section exists
        for (int i = 0; i < lore.size(); i++) {
            if (strippedLore.get(i).contains("Enchantments")) {
                enchantModuleIndex = i;
                break;
            }
        }

        // If enchantments section exists, remove old enchantment entries
        if (enchantModuleIndex != -1) {
            // Remove existing enchant entries (lines with bullets)
            int i = enchantModuleIndex + 1;
            while (i < lore.size() && strippedLore.get(i).contains("•")) {
                lore.remove(i);
                strippedLore.remove(i);
            }

            // Insert new enchantment entries right after the header
            int insertIndex = enchantModuleIndex + 1;
            for (EnchantmentInstance enchantment : enchantments) {
                Optional<Enchantment> optionalEnchantment =
                        enchantRegistry.get(enchantment.getEnchantmentID());

                if (optionalEnchantment.isEmpty()) continue;

                String enchantText = getEnchantText(optionalEnchantment.get(), enchantment.getLevel());
                lore.add(insertIndex++, enchantText);
            }
        } else {
            // Create new enchantments section
            lore.add(" ");
            lore.add(Chat.colorize("&f&lEnchantments"));
            for (EnchantmentInstance enchantmentInstance : enchantments) {
                Optional<Enchantment> optionalEnchantment = enchantRegistry.get(enchantmentInstance.getEnchantmentID());
                if (optionalEnchantment.isEmpty()) continue;
                lore.add(getEnchantText(optionalEnchantment.get(), enchantmentInstance.getLevel()));
            }
            lore.add(" ");
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Converts integer to Roman numeral for enchantment level display
     */
    private String toRomanNumeral(int num) {
        if (num <= 0) return "";
        String[] romanNumerals = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        if (num <= 10) return romanNumerals[num];
        return String.valueOf(num); // Fallback for values above 10
    }

    private String getEnchantText(Enchantment enchantment, int level) {
        boolean maxLevel = enchantment.getMaxLevel() == level;

        return Chat.colorize("&7• " + enchantment.getDisplayName() + (maxLevel ? " &f&l" : " &f") + toRomanNumeral(level));
    }

}
