package com.mineplex.studio.essencepvp.listeners;

import com.mineplex.studio.essencepvp.enchantments.contexts.EnchantmentContext;
import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.items.armor.CustomArmor;
import com.mineplex.studio.essencepvp.registry.impl.EnchantRegistry;
import com.mineplex.studio.essencepvp.utils.Chat;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

/**
 * Handles custom damage calculation for weapons and armor.
 * Overrides Minecraft's default damage calculation.
 */
public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Chat.log(event.getDamageSource().getDamageType().toString());
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
            triggerDefenderEnchantments(defender, attacker, event);
        }

        // Apply defender's armor defense if applicable
        applyArmorDefense(event, defender);
        if (event.getDamage() >= defender.getHealth()) {
            defender.getWorld().spawnParticle(
                    Particle.DUST,
                    defender.getLocation(),
                    100, // Number of particles
                    0.5, 0.5, 0.5, // Spread (X, Y, Z)
                    new org.bukkit.Particle.DustOptions(org.bukkit.Color.RED, 1) // Red particle effect
            );
            causingEntity.getWorld()
                    .playSound(
                            defender.getLocation(),
                            Sound.PARTICLE_SOUL_ESCAPE,
                            3,
                            3
                    );
            event.setCancelled(true);

            Bukkit.getPluginManager()
                    .callEvent(new PlayerDeathEvent(
                            defender,
                            DamageSource.builder(DamageType.GENERIC_KILL)
                                    .withDamageLocation(causingEntity.getLocation())
                                    .withCausingEntity(causingEntity)
                                    .withDirectEntity(causingEntity)
                                    .build(),
                            new ArrayList<>(),
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
                    customWeapon.getEnchantments(weapon)
                            .forEach(enchantmentInstance -> enchantmentInstance.trigger(new EnchantmentContext(weapon,
                                    enchantmentInstance.getLevel(), attacker, event.getEntity(), event)));
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

    private void triggerDefenderEnchantments(Player defender, Player attacker, EntityDamageEvent event) {
        ItemStack[] armorContents = defender.getInventory().getArmorContents();
        for (ItemStack armorPiece : armorContents) {
            if (armorPiece == null) continue;

            CustomItem.getEnchantableItemFromBukkitItem(armorPiece)
                    .ifPresent(customItem -> customItem.getEnchantments(armorPiece)
                            .forEach(enchantment -> EnchantRegistry.getInstance()
                                    .get(enchantment.getEnchantmentID())
                                    .ifPresent(ench -> ench.trigger(new EnchantmentContext(
                                            armorPiece, enchantment.getLevel(), attacker, defender, event
                                    )))));
        }
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