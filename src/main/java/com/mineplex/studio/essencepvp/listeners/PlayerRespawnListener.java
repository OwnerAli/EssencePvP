package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.Essencepvp;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        Bukkit.getScheduler()
                .runTaskLater(Essencepvp.getInstance(), () -> event.getPlayer()
                        .teleport(Essencepvp.getInstance()
                                .getMineplexWorld()
                                .getMinecraftWorld()
                                .getSpawnLocation()), 1);
    }

}
