package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.Essencepvp;
import com.mineplex.studio.essencepvp.loot.context.KillContext;
import com.mineplex.studio.essencepvp.players.PvpPlayer;
import com.mineplex.studio.essencepvp.registry.impl.PvpPlayerRegistry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Optional;

public class PlayerDeathListener implements Listener {

    private final PvpPlayerRegistry pvpPlayerRegistry;

    public PlayerDeathListener() {
        this.pvpPlayerRegistry = PvpPlayerRegistry.getInstance();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player killed = event.getPlayer();

        killed.teleport(Essencepvp.getInstance()
                .getMineplexWorld()
                .getMinecraftWorld()
                .getSpawnLocation());

        Entity killer = event.getDamageSource()
                .getCausingEntity();
        if (killer == null) return;

        Optional<PvpPlayer> victimPvpPlayerOptional = pvpPlayerRegistry.get(killed);

        pvpPlayerRegistry.get(killer.getUniqueId())
                .ifPresent(killerPlayer -> killerPlayer.killOther(
                        new KillContext(victimPvpPlayerOptional.orElseThrow(),
                                killerPlayer
                        )));

        victimPvpPlayerOptional.ifPresent(deadPlayer -> deadPlayer.kill(killer.getUniqueId()));
    }

}