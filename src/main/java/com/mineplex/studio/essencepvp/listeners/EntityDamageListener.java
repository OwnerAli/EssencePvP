package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles custom damage calculation for weapons and armor.
 * Overrides Minecraft's default damage calculation.
 */
public class EntityDamageListener implements Listener {

//    @EventHandler
//    public void onDamage(EntityDamageByEntityEvent event) {
//        // Reset damage to override default Minecraft damage calculation
//        double initialDamage = 0;
//        event.setDamage(initialDamage);
//
//        Entity damager = event.getDamager();
//
//        // Apply attacker's weapon damage if applicable
//        if (damager instanceof Player attacker) {
//            applyWeaponDamage(event, attacker);
//        }
//
//        // Apply defender's armor defense if applicable
//        if (event.getEntity() instanceof Player defender) {
//            applyArmorDefense(event, defender);
//            if (event.getDamage() >= defender.getHealth()) {
//                List<ItemStack> drops = new ArrayList<>();
//
//                Bukkit.getPluginManager()
//                        .callEvent(new PlayerDeathEvent(
//                                defender,
//                                DamageSource.builder(DamageType.GENERIC_KILL)
//                                        .withDamageLocation(damager.getLocation())
//                                        .withCausingEntity(damager)
//                                        .build(),
//                                drops,
//                                0,
//                                Component.empty()
//                        ));
//                defender.setHealth(20);
//            }
//        }
//    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player defender)) return;

        if (event.getDamageSource().getCausingEntity() == null) {
            applyArmorDefense(event, defender);
            return;
        }

        event.setDamage(0);

        Entity causingEntity = event.getDamageSource()
                .getCausingEntity();
        if (causingEntity instanceof Player attacker) {
            applyWeaponDamage(event, attacker);
        }

        // Apply defender's armor defense if applicable
        applyArmorDefense(event, defender);
        if (event.getDamage() >= defender.getHealth()) {
            event.setCancelled(true);
            List<ItemStack> drops = new ArrayList<>();

            Bukkit.getPluginManager()
                    .callEvent(new PlayerDeathEvent(
                            defender,
                            DamageSource.builder(DamageType.GENERIC_KILL)
                                    .withDamageLocation(causingEntity.getLocation())
                                    .withCausingEntity(causingEntity)
                                    .withDirectEntity(causingEntity)
                                    .build(),
                            drops,
                            0,
                            Component.empty()
                    ));
            defender.setHealth(20);
        }
    }

    /**
     * Applies damage from a custom weapon to the event.
     *
     * @param event    The damage event
     * @param attacker The attacking player
     */
    private void applyWeaponDamage(EntityDamageEvent event, Player attacker) {
        ItemStack weapon = attacker.getInventory().getItemInMainHand();

        if (weapon.getType() == Material.AIR) {
            event.setDamage(0.5);
            return;
        }

        CustomItem.getCustomWeaponFromBukkitItem(weapon)
                .ifPresent(customWeapon -> {
                    boolean critical = this.isCritical(attacker);
                    double weaponDamage = customWeapon.getDamage(weapon);

                    Chat.tell(attacker, "&eWeapon Damage: " + weaponDamage);

                    double totalDamage = critical ? weaponDamage * 1.25 : weaponDamage; // add 25% if critical
                    event.setDamage(event.getDamage() + totalDamage);
                    if (critical) {
                        Chat.tell(attacker, "&c&l* CRIT *");
                    }
                    Chat.tell(attacker, "&aYou dealt: " + event.getDamage() + " damage");
                });
    }

    /**
     * Applies defense from custom armor to the event.
     *
     * @param event    The damage event
     * @param defender The defending player
     */
    private void applyArmorDefense(EntityDamageEvent event, Player defender) {
        @Nullable ItemStack[] armorContents = defender.getInventory().getArmorContents();
        double defense = CustomArmor.getDefense(armorContents);

        // Apply defense reduction (but don't allow healing from defense)
        double finalDamage = Math.max(0, event.getDamage() - defense);
        event.setDamage(finalDamage);

        Chat.tell(defender, "&2Defense: " + defense);
        Chat.tell(defender, "&bYou took " + finalDamage + " damage!");
    }

    private boolean isCritical(Player player) {
        return
                player.getFallDistance() > 0.0F &&
                        !player.isOnGround() &&
                        !player.isInsideVehicle() &&
                        !player.hasPotionEffect(PotionEffectType.BLINDNESS) &&
                        player.getLocation().getBlock().getType() != Material.LADDER &&
                        player.getLocation().getBlock().getType() != Material.VINE;
    }

}