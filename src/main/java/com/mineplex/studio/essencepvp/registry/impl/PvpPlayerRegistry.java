package com.mineplex.studio.essencepvp.registry.impl;

import com.mineplex.studio.essencepvp.players.PvpPlayer;
import com.mineplex.studio.essencepvp.registry.Registry;

import java.util.UUID;

public class PvpPlayerRegistry extends Registry<UUID, PvpPlayer> {

    //#region Lazy Initialization
    public static PvpPlayerRegistry getInstance() {
        return PvpPlayerRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final PvpPlayerRegistry instance = new PvpPlayerRegistry();
    }
    //#endregion

}
