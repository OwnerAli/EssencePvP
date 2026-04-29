package com.mineplex.studio.essencepvp.enchantments.damage_enchants;

import com.mineplex.studio.essencepvp.enchantments.Enchantment;
import com.mineplex.studio.essencepvp.enchantments.contexts.EnchantmentContext;
import com.mineplex.studio.essencepvp.enchantments.modifiers.EnchantLevelModifier;
import com.mineplex.studio.essencepvp.enchantments.triggers.damage_trigger.DamageTrigger;
import com.mineplex.studio.essencepvp.items.CustomItem;
import com.mineplex.studio.essencepvp.utils.Chat;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Map;

public class SiphonEnchant extends Enchantment {
    private final EnchantLevelModifier siphonAmount = new EnchantLevelModifier(
            Map.of(
                    1, 3.0,
                    2, 3.5,
                    3, 7.0,
                    4, 7.5,
                    5, 10.0
            )
    );

    public SiphonEnchant() {
        super("siphon", "&e&lSiphon", 5,
                new EnchantLevelModifier(
                        Map.of(
                                1, 0.1,
                                2, 0.2,
                                3, 0.25,
                                4, 0.3,
                                5, 0.35
                        )
                ));
        addTrigger(new DamageTrigger(context -> {
            ItemStack triggerItem = context.triggerItem();
            CustomItem.getLevelableItemFromBukkitItem(triggerItem)
                    .ifPresent(customWeapon -> {
                        if (!(context.defender() instanceof LivingEntity livingEntity)) return;
                        if (!(livingEntity.getHealth() > 6)) return;
                        livingEntity.setHealth(livingEntity.getHealth() - 5);
                        double xpAmount = siphonAmount.getValue(context.level());
                        customWeapon.incrementXP(triggerItem,
                                xpAmount);

                        Chat.tell(context.attacker(), "&6You siphoned &e" + context.defender().getName() +
                                "'s &6health and converted it into &e" + xpAmount + " &6Essence!");
                        Chat.tell(context.defender(), "&6Your were siphoned by " + context.attacker().getName() +
                                ". You've lost &e5 &6HP!");

                        // Draw a line of subtle GOLDEN particles from context.defender
                        // to the attackers weapon
                        drawSiphonParticles(context);
                    });
        }));
    }

    private void drawSiphonParticles(EnchantmentContext context) {
        if (context.defender() == null || context.attacker() == null) return;

        // Get locations
        Location defenderLoc = context.defender().getLocation().add(0, 1, 0); // Center of entity
        Location attackerLoc = context.attacker().getEyeLocation().add(
                context.attacker().getLocation().getDirection().multiply(0.5)); // Near weapon

        World world = defenderLoc.getWorld();
        if (world == null) return;

        // Calculate vector and distance between points
        Vector direction = attackerLoc.toVector().subtract(defenderLoc.toVector());
        double distance = direction.length();
        direction.normalize();

        // Number of particles based on distance
        int particles = (int) (distance * 3);

        // Draw particle line
        for (int i = 0; i < particles; i++) {
            double progress = i / (double) particles;
            Vector position = defenderLoc.toVector().add(direction.clone().multiply(distance * progress));

            world.spawnParticle(
                    Particle.DUST, // Golden-like particle
                    position.getX(),
                    position.getY(),
                    position.getZ(),
                    1, // Count
                    0.02, 0.02, 0.02, // Spread
                    0.0, // Speed
                    new Particle.DustOptions(Color.ORANGE, 1) // Data
            );
        }

        // Add some particle effects at the weapon (destination)
        world.spawnParticle(
                Particle.ELECTRIC_SPARK,
                attackerLoc,
                15,
                0.2, 0.2, 0.2,
                0.0);
    }

}
