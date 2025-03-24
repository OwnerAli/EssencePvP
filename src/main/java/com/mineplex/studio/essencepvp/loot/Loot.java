package com.mineplex.studio.essencepvp.loot;

import com.mineplex.studio.essencepvp.loot.context.KillContext;
import lombok.Getter;

import java.util.function.Predicate;

@Getter
public class Loot {
    protected final Predicate<KillContext> dropCondition;

    protected Loot(final Predicate<KillContext> dropCondition) {
        this.dropCondition = dropCondition;
    }

    /**
     * Processes the loot with the given context.
     *
     * @param context the context in which the loot is processed
     */
    public boolean processes(final KillContext context) {
        return this.dropCondition.test(context);
    }

}