package dev.jkopecky.titanstrials.data;

import dev.jkopecky.titanstrials.TitansTrials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TitansTrials.MOD_ID)
public class CapabilityEvents {

    //on death, we need to copy the capability data to the new player clone
    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            Capability<DifficultyCapability> cap = DifficultyCapabilityProvider.difficulty;
            event.getOriginal().getCapability(cap)
                    .ifPresent(old ->
                            event.getEntity().getCapability(cap)
                            .ifPresent(newCap -> {newCap.copyFrom(old);}));
        }
    }

    /* //example of getting the difficulty from the player given an instance of the player
    @SubscribeEvent
    public static void onPlayerJoinEvent(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        player.getCapability(DifficultyCapabilityProvider.difficulty).ifPresent(cap -> {
            cap.getDifficulty();
        });
    }
     */

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            //if capability does not exist yet, add it
            if (!player.getCapability(DifficultyCapabilityProvider.difficulty).isPresent()) {
                ResourceLocation location = ResourceLocation.fromNamespaceAndPath(
                        TitansTrials.MOD_ID, "titanstrials_difficulty");
                event.addCapability(location, new DifficultyCapabilityProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(DifficultyCapability.class);
    }
}
