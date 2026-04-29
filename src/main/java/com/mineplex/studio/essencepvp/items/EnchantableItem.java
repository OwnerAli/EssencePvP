package com.mineplex.studio.essencepvp.items;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mineplex.studio.essencepvp.Essencepvp;
import com.mineplex.studio.essencepvp.enchantments.EnchantmentInstance;
import com.mineplex.studio.essencepvp.items.display_modules.item_lore_modules.EnchantsModule;
import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public interface EnchantableItem extends DisplayProvider {
    NamespacedKey ENCHANTS_KEY = new NamespacedKey(Essencepvp.getInstance(), "enchants");
    Gson GSON = new Gson();

    default void applyEnchantableDataToItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(ENCHANTS_KEY, PersistentDataType.STRING, "");
        itemStack.setItemMeta(itemMeta);
    }

    default void addEnchantmentOrUpdateLevel(ItemStack itemStack, String enchantmentID, int newLevel) {
        List<EnchantmentInstance> enchantments = getEnchantments(itemStack);

        if (enchantments == null) return;

        // Find and update the level of the enchantment if it exists
        for (EnchantmentInstance enchantment : enchantments) {
            if (!enchantment.getEnchantmentID().equals(enchantmentID)) continue;
            enchantment.setLevel(enchantment.getLevel() + newLevel); // Update the level
            updateEnchantments(itemStack, enchantments);
            return;
        }

        // If not found, add it as a new enchantment
        EnchantmentInstance newEnchantment = new EnchantmentInstance(enchantmentID, newLevel);
        enchantments.add(newEnchantment);
        updateEnchantments(itemStack, enchantments);
    }

    default void incrementEnchantmentLevel(ItemStack itemStack, String enchantmentID) {
        List<EnchantmentInstance> enchantments = getEnchantments(itemStack);

        if (enchantments == null) return;

        enchantments.stream().filter(enchantmentInstance -> enchantmentInstance.getEnchantmentID()
                        .equals(enchantmentID))
                .findAny()
                .ifPresent(EnchantmentInstance::incrementLevel);
        updateEnchantments(itemStack, enchantments);
    }

    default @Nullable List<EnchantmentInstance> getEnchantments(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return new ArrayList<>();

        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        String json = pdc.get(ENCHANTS_KEY, PersistentDataType.STRING);

        if (json == null || json.isEmpty()) return new ArrayList<>();

        try {
            Type listType = new TypeToken<List<EnchantmentInstance>>() {
            }.getType();
            return GSON.fromJson(json, listType);
        } catch (Exception e) {
            Chat.log("Failed to parse enchantments: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private void updateEnchantments(ItemStack itemStack, List<EnchantmentInstance> enchantments) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return;

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        String json = GSON.toJson(enchantments);

        pdc.set(ENCHANTS_KEY, PersistentDataType.STRING, json);
        itemStack.setItemMeta(itemMeta);
        getDisplayModules().stream().filter(displayModule -> displayModule instanceof EnchantsModule)
                .findAny()
                .ifPresent(module -> module.update(itemStack));
    }

}
