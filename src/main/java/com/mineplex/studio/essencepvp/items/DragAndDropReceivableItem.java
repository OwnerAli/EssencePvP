package com.mineplex.studio.essencepvp.items;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface DragAndDropReceivableItem {

    default void receiveDragAndDrop(InventoryClickEvent event) {
        event.setCancelled(true);

        if (event.getWhoClicked() instanceof Player player) {
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
        }
        ItemStack cursorItem = event.getCursor();
        cursorItem.setAmount(cursorItem.getAmount() - 1);
    }

}
