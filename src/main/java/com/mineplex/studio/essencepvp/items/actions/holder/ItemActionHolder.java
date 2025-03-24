package com.mineplex.studio.essencepvp.items.actions.holder;

import com.mineplex.studio.essencepvp.items.actions.CustomItemAction;
import com.mineplex.studio.essencepvp.items.actions.context.ActionContext;

import java.util.Set;

public class ItemActionHolder {
    private final Set<CustomItemAction> itemActions;

    public ItemActionHolder(Set<CustomItemAction> itemActions) {
        this.itemActions = itemActions;
    }

    public void executeAllActions(ActionContext context) {
        itemActions.forEach(customItemAction -> customItemAction.apply(context));
    }

}
