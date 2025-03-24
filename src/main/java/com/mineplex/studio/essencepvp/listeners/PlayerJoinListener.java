package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.loot.context.KillContext;
import com.mineplex.studio.essencepvp.loot.impl.XPLoot;
import com.mineplex.studio.essencepvp.players.PvpPlayer;
import com.mineplex.studio.essencepvp.registry.impl.PvpPlayerRegistry;
import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(null);

        event.getPlayer().getAttribute(Attribute.ATTACK_SPEED)
                .setBaseValue(24D);
        event.getPlayer().setCooldown(Material.WIND_CHARGE, 0);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Chat.log("DEATH!");
        final var victimUUID = event.getPlayer().getUniqueId();
        final var killerUUID = event.getEntity().getUniqueId();

        Optional<PvpPlayer> victimPvpPlayer = PvpPlayerRegistry.getInstance().get(victimUUID);
        Optional<PvpPlayer> killerPvpPlayer = PvpPlayerRegistry.getInstance().get(killerUUID);

        if (victimPvpPlayer.isEmpty() || killerPvpPlayer.isEmpty()) return;
        PvpPlayer victimPlayer = victimPvpPlayer.get();
        PvpPlayer killerPlayer = killerPvpPlayer.get();

        KillContext killContext = new KillContext(victimPlayer, killerPlayer);
        new XPLoot(10).processes(killContext);
    }

}