package com.mineplex.studio.essencepvp.items.actions.impl;

import com.mineplex.studio.essencepvp.items.actions.CustomItemAction;
import com.mineplex.studio.essencepvp.items.actions.context.ActionContext;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.Consumer;

public class PlayerInteractAction implements CustomItemAction {
    private final Consumer<PlayerInteractEvent> playerInteractEventConsumer;

    public PlayerInteractAction(Consumer<PlayerInteractEvent> playerInteractEventConsumer) {
        this.playerInteractEventConsumer = playerInteractEventConsumer;
    }

    @Override
    public boolean apply(ActionContext context) {
        if (context.event() == null) return false;
        if (!(context.event() instanceof PlayerInteractEvent
                playerInteractEvent)) return false;
        playerInteractEventConsumer.accept(playerInteractEvent);
        return true;
    }

}
