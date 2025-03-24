package com.mineplex.studio.essencepvp.items.actions.impl;

import com.mineplex.studio.essencepvp.items.actions.CustomItemAction;
import com.mineplex.studio.essencepvp.items.actions.context.ActionContext;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.function.Consumer;

public class InventoryClickAction implements CustomItemAction {

    private final Consumer<InventoryClickEvent> eventConsumer;

    public InventoryClickAction(Consumer<InventoryClickEvent> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @Override
    public boolean apply(ActionContext context) {
        if (context.event() == null) return false;
        if (!(context.event() instanceof InventoryClickEvent
                inventoryClickEvent)) return false;
        eventConsumer.accept(inventoryClickEvent);
        return true;
    }

}
