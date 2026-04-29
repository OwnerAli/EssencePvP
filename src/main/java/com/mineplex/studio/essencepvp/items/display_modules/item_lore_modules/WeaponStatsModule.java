package com.mineplex.studio.essencepvp.items.display_modules.item_lore_modules;

import com.mineplex.studio.essencepvp.items.display_modules.DisplayModule;
import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.utils.Chat;
import com.mineplex.studio.essencepvp.utils.FormatUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class WeaponStatsModule implements DisplayModule {
    private final CustomWeapon customWeapon;

    public WeaponStatsModule(CustomWeapon customWeapon) {
        this.customWeapon = customWeapon;
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

                for (int i = 0; i < strip.size(); i++) {
                    if (strip.get(i).contains("Weapon Stats")) {
                        lore.remove(i - 1);
                        lore.remove(i);
                        lore.remove(i + 1);
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

        String defenseValue = Chat.colorize("&7⚔ Damage: &f" + getDefenseStat(itemStack)); // Add "Defense: " here

        boolean updated = false;
        for (int i = 0; i < lore.size(); i++) {
            if (strippedLore.get(i).contains("Weapon Stats")) {
                lore.set(i + 1, defenseValue); // Ensure it's only updated, not duplicated
                updated = true;
                break;
            }
        }

        if (!updated) {
            lore.add(" ");
            lore.add(Chat.colorize("&f&lWeapon Stats"));
            lore.add(defenseValue); // Now correctly formatted
            lore.add(" ");
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    private String getDefenseStat(ItemStack itemStack) {
        double damage = customWeapon.calculateDamage(customWeapon.getLevel(itemStack));
        return FormatUtils.formatNumber(damage); // Only return the formatted number
    }

}