package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.actions.context.ActionContext;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        CustomItem.getActionableItemFromBukkitItem(event.getCurrentItem())
                .ifPresent(actionableItem -> {
                    actionableItem.getActionHolder().executeAllActions(
                            ActionContext.builder()
                                    .event(event)
                                    .build()
                    );
                });
    }

}
