package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.actions.context.ActionContext;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        CustomItem.getActionableItemFromBukkitItem(event.getItem())
                .ifPresent(actionableItem -> {
                    actionableItem.getActionHolder().executeAllActions(
                            ActionContext.builder()
                                    .event(event)
                                    .build()
                    );
                });
    }

}
