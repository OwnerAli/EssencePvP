package com.mineplex.studio.essencepvp.players;

import com.mineplex.studio.essencepvp.loot.context.KillContext;
import com.mineplex.studio.essencepvp.registry.impl.LootRegistry;
import com.mineplex.studio.essencepvp.stats.PvpStat;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public class PvpPlayer {
    private final Player player;
    private final PvpStat pvpStat;

    public PvpPlayer(Player player) {
        this.player = player;
        pvpStat = new PvpStat();
    }

    public void killOther(KillContext killContext) {
        PvpPlayer victim = killContext.victim();
        UUID victimUUID = victim
                .getPlayer()
                .getUniqueId();

        pvpStat.addKillForKilled(victimUUID);
        pvpStat.setKillStreak(pvpStat.getKillStreak() + 1);

        LootRegistry.getInstance()
                .get("default_item_xp")
                .ifPresent(loot -> loot.processes(killContext));
    }

    public void kill(UUID killerUUID) {
        pvpStat.addDeathForKiller(killerUUID);
        pvpStat.setKillStreak(0);
    }

}
