package com.mineplex.studio.essencepvp.players;

import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.entity.Player;

public class PvpPlayer {
    private final Player player;

    public PvpPlayer(Player player) {
        this.player = player;
    }

    public void addXP(double xpAmount) {
        Chat.tell(player, "&a&l+" + xpAmount + " XP!");
    }

}
