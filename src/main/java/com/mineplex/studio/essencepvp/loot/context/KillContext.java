package com.mineplex.studio.essencepvp.loot.context;

import com.mineplex.studio.essencepvp.players.PvpPlayer;

public record KillContext(PvpPlayer victim, PvpPlayer killer) {
}
