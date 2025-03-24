package com.mineplex.studio.essencepvp.items.actions;

import com.mineplex.studio.essencepvp.items.actions.context.ActionContext;

public interface CustomItemAction {

    /**
     * Applies the metadata logic to a given context.
     *
     * @param context The context for the metadata, e.g., an event or a player action.
     * @return true if the condition passes, false otherwise.
     */
    boolean apply(ActionContext context);

}
