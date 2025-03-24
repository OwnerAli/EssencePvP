package com.mineplex.studio.essencepvp.loot.impl;

import com.mineplex.studio.essencepvp.loot.Loot;
import com.mineplex.studio.essencepvp.loot.context.KillContext;

public class XPLoot extends Loot {
    private final double xpAmount;

    public XPLoot(double xpAmount) {
        super(player -> true);
        this.xpAmount = xpAmount;
    }

    @Override
    public boolean processes(KillContext context) {
        if (!super.processes(context)) return false;
        context.killer().addXP(xpAmount);
        return true;
    }

}
