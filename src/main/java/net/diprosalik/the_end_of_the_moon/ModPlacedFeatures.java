package net.diprosalik.the_end_of_the_moon;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> ZENITH_SHROOM_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            Identifier.of("the-end-of-the-moon", "zenith_shroom_placed"));
    public static final RegistryKey<PlacedFeature> CHORUS_SHROOM_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            Identifier.of("the-end-of-the-moon", "chorus_shroom_placed"));

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        context.register(ZENITH_SHROOM_PLACED_KEY, new PlacedFeature(
                configuredFeatureLookup.getOrThrow(ModConfiguredFeatures.ZENITH_SHROOM_KEY),
                List.of(
                        RarityFilterPlacementModifier.of(48), // Etwas seltener gemacht
                        SquarePlacementModifier.of(),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        BiomePlacementModifier.of()
                )
        ));

        context.register(CHORUS_SHROOM_PLACED_KEY, new PlacedFeature(
                configuredFeatureLookup.getOrThrow(ModConfiguredFeatures.CHORUS_SHROOM_PATCH_KEY),
                List.of(
                        CountPlacementModifier.of(4), // 4 Gruppen pro Chunk -> Häufig!
                        SquarePlacementModifier.of(),
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
                        BiomePlacementModifier.of()
                )));
    }
}