package net.diprosalik.the_end_of_the_moon;

import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.diprosalik.the_end_of_the_moon.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheEndOfTheMoon implements ModInitializer {
	public static final String MOD_ID = "the-end-of-the-moon";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlock.registerModBlocks();

		//registerWorldGen();

		ChorusFlower.ClickOnFlowerToHarvest();

		LootTableEvents.REPLACE.register((key, original, source, registries) -> {
			// Prüfen, ob es die Loot-Table der Vanilla Chorus Blume ist
			if (Blocks.CHORUS_FLOWER.getLootTableKey().equals(key)) {

				// Wir bauen eine komplett neue Loot-Table
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(UniformLootNumberProvider.create(1.0f, 4.0f)) // 0 bis 2 Drops
						.with(ItemEntry.builder(ModItems.CHORUS_SEEDS));    // Nur deine Seeds

				return LootTable.builder().pool(poolBuilder).build();
			}
			return null; // Andere Loot-Tables werden nicht verändert
		});
	}

	public static void registerWorldGen() {
		BiomeModifications.addFeature(
				BiomeSelectors.foundInTheEnd(), // Entspricht BiomeKeys.THE_END etc.
				GenerationStep.Feature.VEGETAL_DECORATION,
				ModPlacedFeatures.CHORUS_SHROOM_PLACED_KEY // Dein Key von oben
		);
	}
}