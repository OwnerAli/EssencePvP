package com.mineplex.studio.essencepvp.items;

import com.mineplex.studio.essencepvp.Essencepvp;
import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.items.weapons.CustomWeapon;
import com.mineplex.studio.essencepvp.registry.impl.ItemRegistry;
import com.mineplex.studio.essencepvp.utils.Chat;
import io.papermc.paper.persistence.PersistentDataContainerView;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

@Getter
public abstract class CustomItem {
    public static final NamespacedKey CUSTOM_ITEM_KEY = new NamespacedKey(Essencepvp.getInstance(), "custom-item");
    private final String id;
    private final ItemStack item;
    private final String displayName;

    protected CustomItem(String id, ItemStack item, String displayName) {
        this.id = id;
        this.item = item;
        this.displayName = Chat.colorize(displayName);
    }

    /**
     * Retrieves the CustomItem from the given ItemStack.
     *
     * @param itemStack the ItemStack to retrieve the CustomItem from
     * @return an Optional containing the CustomItem if present, otherwise an empty Optional
     */
    public static Optional<CustomItem> getCustomItemFromBukkitItem(ItemStack itemStack) {
        // Get the persistent data container from the item's metadata
        PersistentDataContainerView pdc = itemStack.getPersistentDataContainer();

        // Custom item exist if PDC is empty
        if (pdc.isEmpty()) return Optional.empty();

        // Retrieve the custom item ID stored in the container
        String customItemId = pdc.get(CUSTOM_ITEM_KEY, PersistentDataType.STRING);

        // Look up the custom item in the registry
        return ItemRegistry.getInstance().get(customItemId);
    }

    /**
     * Retrieves the CustomWeapon from the given ItemStack.
     *
     * @param itemStack the ItemStack to retrieve the CustomWeapon from
     * @return an Optional containing the CustomWeapon if present, otherwise an empty Optional
     */
    public static Optional<CustomWeapon> getCustomWeaponFromBukkitItem(ItemStack itemStack) {
        // First get the CustomItem from the ItemStack
        Optional<CustomItem> customItemFromBukkitItem = getCustomItemFromBukkitItem(itemStack);

        // Return empty if no CustomItem was found
        if (customItemFromBukkitItem.isEmpty()) return Optional.empty();

        // Filter for CustomWeapon type, cast it, and return the first match
        return customItemFromBukkitItem.stream()
                .filter(customItem -> customItem instanceof CustomWeapon)
                .map(customItem -> (CustomWeapon) customItem)
                .findFirst();
    }

    /**
     * Retrieves the CustomArmor from the given ItemStack.
     *
     * @param itemStack the ItemStack to retrieve the CustomArmor from
     * @return an Optional containing the CustomArmor if present, otherwise an empty Optional
     **/
    public static Optional<CustomArmor> getCustomArmorFromBukkitItem(ItemStack itemStack) {
        // First get the CustomItem from the ItemStack
        Optional<CustomItem> customItemFromBukkitItem = getCustomItemFromBukkitItem(itemStack);

        // Return empty if no CustomItem was found
        if (customItemFromBukkitItem.isEmpty()) return Optional.empty();

        // Filter for CustomArmor type, cast it, and return the first match
        return customItemFromBukkitItem.stream()
                .filter(customItem -> customItem instanceof CustomArmor)
                .map(customItem -> (CustomArmor) customItem)
                .findFirst();
    }

    public static Optional<LevelableItem> getLevelableItemFromBukkitItem(ItemStack itemStack) {
        // First get the CustomItem from the ItemStack
        Optional<CustomItem> customItemFromBukkitItem = getCustomItemFromBukkitItem(itemStack);

        // Return empty if no CustomItem was found
        if (customItemFromBukkitItem.isEmpty()) return Optional.empty();

        // Filter for CustomWeapon type, cast it, and return the first match
        return customItemFromBukkitItem.stream()
                .filter(customItem -> customItem instanceof LevelableItem)
                .map(customItem -> (LevelableItem) customItem)
                .findFirst();
    }

    public static Optional<ActionableItem> getActionableItemFromBukkitItem(ItemStack itemStack) {
        // First get the CustomItem from the ItemStack
        Optional<CustomItem> customItemFromBukkitItem = getCustomItemFromBukkitItem(itemStack);

        // Return empty if no CustomItem was found
        if (customItemFromBukkitItem.isEmpty()) return Optional.empty();

        // Filter for CustomWeapon type, cast it, and return the first match
        return customItemFromBukkitItem.stream()
                .filter(customItem -> customItem instanceof ActionableItem)
                .map(customItem -> (ActionableItem) customItem)
                .findFirst();
    }

    public static void updateLevelDisplay(ItemStack itemStack) {
        Optional<CustomItem> customItemFromBukkitItem = getCustomItemFromBukkitItem(itemStack);
        customItemFromBukkitItem.ifPresent(customItem -> {
            if (!(customItem instanceof LevelableItem levelableItem)) return;
            String itemDisplayName = customItem.getDisplayName().replace("$level",
                    String.valueOf(levelableItem.getLevel(itemStack)));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(Component.text(itemDisplayName));
            itemStack.setItemMeta(itemMeta);
        });
    }

    public ItemStack createBukkitItem() {
        ItemStack itemStack = item.clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer()
                .set(CUSTOM_ITEM_KEY, PersistentDataType.STRING, id);
        itemMeta.displayName(Component.text(displayName));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack createBukkitItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer()
                .set(CUSTOM_ITEM_KEY, PersistentDataType.STRING, id);
        itemMeta.displayName(Component.text(displayName));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public abstract void applyPlaceholders();

}
