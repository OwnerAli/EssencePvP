package com.mineplex.studio.essencepvp.stats;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class PvpStat {
    private final Map<UUID, Integer> playerKills;
    private final Map<UUID, Integer> playerDeaths;
    private int killStreak;

    public PvpStat() {
        this.playerKills = new HashMap<>();
        this.playerDeaths = new HashMap<>();
    }

    public void addKillForKilled(UUID killedUUID) {
        Integer kills = playerKills.getOrDefault(killedUUID, 0);
        playerKills.put(killedUUID, kills + 1);
    }

    public void addDeathForKiller(UUID killerUUID) {
        Integer deaths = playerDeaths.getOrDefault(killerUUID, 0);
        playerKills.put(killerUUID, deaths + 1);
    }

    public boolean onKillStreak() {
        return killStreak > 1;
    }

}
