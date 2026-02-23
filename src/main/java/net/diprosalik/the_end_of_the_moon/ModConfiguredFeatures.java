package net.diprosalik.the_end_of_the_moon;

import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> ZENITH_SHROOM_KEY = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
            Identifier.of("the-end-of-the-moon", "zenith_shroom"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> CHORUS_SHROOM_PATCH_KEY = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE,
            Identifier.of("the-end-of-the-moon", "chorus_shroom_patch"));

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        context.register(ZENITH_SHROOM_KEY, new ConfiguredFeature<>(Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlock.ZENITH_SHROOM))));

        context.register(CHORUS_SHROOM_PATCH_KEY, new ConfiguredFeature<>(Feature.FLOWER,
                new RandomPatchFeatureConfig(
                        32, // Versuche pro Gruppe
                        7,  // Streuung (Radius)
                        3,  // Vertikale Streuung
                        PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK,
                                new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlock.CHORUS_SHROOM)))
                )));
    }


}