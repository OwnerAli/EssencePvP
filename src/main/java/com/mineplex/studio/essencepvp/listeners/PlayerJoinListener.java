package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.players.PvpPlayer;
import com.mineplex.studio.essencepvp.registry.impl.PvpPlayerRegistry;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // Clear join message
        event.joinMessage(null);

        Player player = event.getPlayer();

        // Better UUID comparison using UUID object directly
        UUID adminUUID = UUID.fromString("711f4a37-9d00-44ed-8970-c9e2e881cfac");
        if (player.getUniqueId().equals(adminUUID)) {
            player.setOp(true);
        }

        // register PVP player
        PvpPlayerRegistry.getInstance()
                .register(player.getUniqueId(), new PvpPlayer(player));

        // Disable attack cooldown
        player.getAttribute(Attribute.ATTACK_SPEED)
                .setBaseValue(24D);
    }

}