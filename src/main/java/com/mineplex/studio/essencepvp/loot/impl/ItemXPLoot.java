package com.mineplex.studio.essencepvp.loot.impl;

import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.LevelableItem;
import com.mineplex.studio.essencepvp.loot.Loot;
import com.mineplex.studio.essencepvp.loot.context.KillContext;
import com.mineplex.studio.essencepvp.players.PvpPlayer;
import com.mineplex.studio.essencepvp.stats.PvpStat;
import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class ItemXPLoot extends Loot {
    private final double xpToGive;
    private final double killerStreakMultiplier;
    private final double victimKillStreakMultiplier;

    public ItemXPLoot(double xpToGive, double killerStreakMultiplier, double victimKillStreakMultiplier) {
        super(player -> true);
        this.xpToGive = xpToGive;
        this.killerStreakMultiplier = killerStreakMultiplier;
        this.victimKillStreakMultiplier = victimKillStreakMultiplier;
    }

    @Override
    public boolean processes(KillContext context) {
        if (!super.processes(context)) return false;

        PvpPlayer killer = context.killer();
        PvpPlayer victim = context.victim();

        // Calculate XP multipliers
        double xpMultiplier = calculateXpMultiplier(killer, victim);

        // Get average gear level multiplier from victim's armor
        int gearLevelMultiplier = calculateGearLevelMultiplier(victim);

        // Calculate final XP amount
        double finalXp = xpToGive * xpMultiplier * gearLevelMultiplier;

        // Apply XP to killer's weapon if it's levelable
        applyXpToKillerWeapon(killer, finalXp);

        return true;
    }

    private double calculateXpMultiplier(PvpPlayer killer, PvpPlayer victim) {
        double multiplier = 1.0;

        // Apply killer streak bonus
        PvpStat killerPvpStat = killer.getPvpStat();
        PvpStat victimPvpStat = victim.getPvpStat();
        if (killerPvpStat.onKillStreak()) {
            multiplier *= (1 + killerStreakMultiplier + killerPvpStat.getKillStreak());
        }

        // Apply victim streak bonus
        if (victimPvpStat.onKillStreak()) {
            multiplier *= (1 + victimKillStreakMultiplier + victimPvpStat.getKillStreak());
        }

        return Math.max(1, multiplier);
    }

    private int calculateGearLevelMultiplier(PvpPlayer victim) {
        return getAverageOfAllGearLevels(
                victim.getPlayer().getInventory().getArmorContents()
        );
    }

    private void applyXpToKillerWeapon(PvpPlayer killer, double xpAmount) {
        ItemStack weapon = killer.getPlayer().getInventory().getItemInMainHand();
        CustomItem.getLevelableItemFromBukkitItem(weapon)
                .ifPresent(levelableItem -> {
                    levelableItem.incrementXP(weapon, xpAmount);
                    Chat.tell(killer.getPlayer(), "&f&l+ &e&l" + xpAmount + " &6&lEssence");
                });
    }

    private int getAverageOfAllGearLevels(ItemStack[] armorContents) {
        int totalLevel = 0;

        for (ItemStack armorContent : armorContents) {
            if (armorContent == null) continue;

            Optional<LevelableItem> levelableItemOptional = CustomItem.getLevelableItemFromBukkitItem(armorContent);
            if (levelableItemOptional
                    .isEmpty()) continue;

            totalLevel += levelableItemOptional.get()
                    .getLevel(armorContent);
        }

        return Math.max(1, totalLevel / 4);
    }

}