package net.diprosalik.the_end_of_the_moon;

import net.diprosalik.the_end_of_the_moon.TheEndOfTheMoon;
import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import java.util.List;

public class ModPlacedFeatures {
    // Der Key, den wir später für die Biome-Modifikation brauchen
    public static final RegistryKey<PlacedFeature> CHORUS_SHROOM_PLACED_KEY = RegistryKey.of(RegistryKeys.PLACED_FEATURE,
            Identifier.of(TheEndOfTheMoon.MOD_ID, "chorus_shroom_patch"));

    public static void bootstrap(Registerable<PlacedFeature> context) {
        // Wir holen uns den Zugriff auf die Features
        var configuredFeatureRegistryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        // Hier definieren wir das "Configured Feature" direkt im Code (was sonst im JSON stünde)
        ConfiguredFeature<?, ?> shroomPatch = new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(6,
                        PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlock.CHORUS_SHROOM)))));

        // Wir registrieren das PlacedFeature mit Filtern (z.B. Seltenheit)
        context.register(CHORUS_SHROOM_PLACED_KEY, new PlacedFeature(
                RegistryEntry.of(shroomPatch),
                List.of(
                        RarityFilterPlacementModifier.of(16), // Chance 1 von 16 pro Chunk
                        SquarePlacementModifier.of(),         // In der Fläche verteilen
                        PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, // Auf der Oberfläche platzieren
                        BiomePlacementModifier.of()           // Nur im richtigen Biom
                )
        ));
    }
}