package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.Essencepvp;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class PlayerSpawnLocationListener implements Listener {

    @EventHandler
    public void onSpawn(PlayerSpawnLocationEvent event) {
        event.setSpawnLocation(Essencepvp.getInstance().getMineplexWorld().getMinecraftWorld().getSpawnLocation());
    }

}
