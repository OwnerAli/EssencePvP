package com.mineplex.studio.essencepvp.registry.impl;

import com.mineplex.studio.essencepvp.players.PvpPlayer;
import com.mineplex.studio.essencepvp.registry.Registry;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class PvpPlayerRegistry extends Registry<UUID, PvpPlayer> {

    public Optional<PvpPlayer> get(Player key) {
        return super.get(key.getUniqueId());
    }

    //#region Lazy Initialization
    public static PvpPlayerRegistry getInstance() {
        return PvpPlayerRegistry.InstanceHolder.instance;
    }

    private static final class InstanceHolder {
        private static final PvpPlayerRegistry instance = new PvpPlayerRegistry();
    }
    //#endregion

}