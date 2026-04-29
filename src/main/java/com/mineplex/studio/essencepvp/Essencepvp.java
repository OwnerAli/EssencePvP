package com.mineplex.studio.essencepvp;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.EventManager;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.mineplex.studio.essencepvp.commands.AdminCommands;
import com.mineplex.studio.essencepvp.commands.EnchantCommand;
import com.mineplex.studio.essencepvp.commands.GiveCustomItemCommand;
import com.mineplex.studio.essencepvp.commands.LevelUpCommand;
import com.mineplex.studio.essencepvp.listeners.*;
import com.mineplex.studio.essencepvp.packets.listeners.AttackSoundsPacketListener;
import com.mineplex.studio.essencepvp.packets.listeners.SweepParticlePacketListener;
import com.mineplex.studio.essencepvp.registry.impl.EnchantRegistry;
import com.mineplex.studio.essencepvp.registry.impl.ItemRegistry;
import com.mineplex.studio.essencepvp.registry.impl.LootRegistry;
import com.mineplex.studio.sdk.modules.MineplexModuleManager;
import com.mineplex.studio.sdk.modules.command.CommandModule;
import com.mineplex.studio.sdk.modules.game.*;
import com.mineplex.studio.sdk.modules.world.MineplexWorld;
import com.mineplex.studio.sdk.modules.world.MineplexWorldModule;
import com.mineplex.studio.sdk.modules.world.config.MineplexWorldConfig;
import com.mineplex.studio.sdk.modules.world.config.WorldCreationConfig;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class Essencepvp extends JavaPlugin implements SingleWorldMineplexGame {
    private MineplexWorldModule mineplexWorldModule;

    @Getter
    public static Essencepvp instance;

    @Getter
    private MineplexWorld mineplexWorld;

    @Getter
    private MineplexGameMechanicFactory mineplexGameMechanicFactory;

    @Getter
    Random random;

    @Override
    public void onLoad() {
        EventManager eventManager = PacketEvents.getAPI()
                .getEventManager();
        eventManager.registerListener(new SweepParticlePacketListener(),
                PacketListenerPriority.NORMAL);
        eventManager.registerListener(new AttackSoundsPacketListener(),
                PacketListenerPriority.NORMAL);
        this.random = new Random();
    }

    @Override
    public void onEnable() {
        instance = this;

        registerListeners();
        registerCommands();

        mineplexWorldModule = MineplexModuleManager
                .getRegisteredModule(MineplexWorldModule.class);

//        generateNewPersistentWorld()
//                .thenAccept(newWrld -> {
//                    this.mineplexWorld = newWrld;
//                });

        this.mineplexWorld = generateNewPersistentWorld();

        mineplexGameMechanicFactory = MineplexModuleManager
                .getRegisteredModule(MineplexGameMechanicFactory.class);
        mineplexGameMechanicFactory.setup();

        ItemRegistry.getInstance()
                .init();
        LootRegistry.getInstance()
                .init();
        EnchantRegistry.getInstance()
                .init();
    }

    public @NonNull MineplexWorld generateNewPersistentWorld() {
        // Creates and generates a new persistent world cached on the filesystem
        // using the anvil region format and a seed

//        return mineplexWorldModule.loadOrCreateMineplexWorld("Examples",
//                "EssencePvP", MineplexWorldConfig.builder()
//                        .persistentWorldConfig(PersistentWorldConfig.builder()
//                                .worldBucket("Examples")
//                                .build())
//                        .worldCreationConfig(WorldCreationConfig.builder()
//                                .build())
//                        .build());
        return mineplexWorldModule.createMineplexWorld(MineplexWorldConfig.builder()
                .worldCreationConfig(WorldCreationConfig.builder()
                        .build())
                .build(), "EssencePVP");
    }

    @Override
    public @NonNull MineplexWorld getGameWorld() {
        return mineplexWorld;
    }

    @Override
    public @NonNull MineplexGameMechanicFactory getGameMechanicFactory() {
        return MineplexModuleManager.getRegisteredModule(MineplexGameMechanicFactory.class);
    }

    @Override
    public @NonNull GameState getGameState() {
        return BuiltInGameState.PRE_START;
    }

    @Override
    public void setGameState(@NonNull GameState gameState) {

    }

    @Override
    public @NonNull PlayerState getPlayerState(@NonNull Player player) {
        return null;
    }

    @Override
    public void setup() {

    }

    @Override
    public void teardown() {

    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new PlayerSpawnLocationListener(), this);
        pluginManager.registerEvents(new PlayerRespawnListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new ItemDamageListener(), this);
    }

    private void registerCommands() {
        CommandModule commandModule = MineplexModuleManager.getRegisteredModule(CommandModule.class);
        commandModule.register("epvp", new LevelUpCommand());
        commandModule.register("epvp", new GiveCustomItemCommand());
        commandModule.register("epvp", new AdminCommands());
        commandModule.register("epvp", new EnchantCommand());
    }

}