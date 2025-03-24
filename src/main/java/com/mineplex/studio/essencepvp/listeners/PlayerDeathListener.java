package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.Essencepvp;
import com.mineplex.studio.essencepvp.registry.impl.PvpPlayerRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final PvpPlayerRegistry pvpPlayerRegistry;

    public PlayerDeathListener() {
        this.pvpPlayerRegistry = PvpPlayerRegistry.getInstance();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getPlayer().teleport(Essencepvp.getInstance()
                .getMineplexWorld()
                .getMinecraftWorld()
                .getSpawnLocation());
//        final var victimUUID = event.getPlayer().getUniqueId();
//
//        Player killer = event.getPlayer().getKiller();
//        if (killer == null) return;
//        final var killerUUID = killer.getUniqueId();
//
//        Optional<PvpPlayer> victimPvpPlayer = pvpPlayerRegistry.get(victimUUID);
//        Optional<PvpPlayer> killerPvpPlayer = pvpPlayerRegistry.get(killerUUID);
//
//        if (victimPvpPlayer.isEmpty() || killerPvpPlayer.isEmpty()) return;
//        PvpPlayer victimPlayer = victimPvpPlayer.get();
//        PvpPlayer killerPlayer = killerPvpPlayer.get();
//
//        KillContext killContext = new KillContext(victimPlayer, killerPlayer);
//        new XPLoot(10).processes(killContext);
    }

}