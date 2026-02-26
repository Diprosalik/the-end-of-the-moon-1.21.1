package net.diprosalik.the_end_of_the_moon;

import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.diprosalik.the_end_of_the_moon.item.ModItems;
import net.diprosalik.the_end_of_the_moon.particle.ChorusShroomParticle;
import net.diprosalik.the_end_of_the_moon.particle.ModParticles;
import net.diprosalik.the_end_of_the_moon.potion.ModPotions;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.potion.Potions;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheEndOfTheMoon implements ModInitializer {
	public static final String MOD_ID = "the-end-of-the-moon";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlock.registerModBlocks();

		ModPotions.registerPotions();

		ChorusFlower.ClickOnFlowerToHarvest();

		LootTableEvents.REPLACE.register((key, original, source, registries) -> {
			if (Blocks.CHORUS_FLOWER.getLootTableKey().equals(key)) {

				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(UniformLootNumberProvider.create(1.0f, 4.0f))
						.with(ItemEntry.builder(ModItems.CHORUS_SEEDS));

				return LootTable.builder().pool(poolBuilder).build();
			}
			return null;
		});

		BiomeModifications.addFeature(
				BiomeSelectors.foundInTheEnd().and(BiomeSelectors.excludeByKey(BiomeKeys.THE_END)),
				GenerationStep.Feature.VEGETAL_DECORATION,
				ModPlacedFeatures.CHORUS_SHROOM_PLACED_KEY
		);


		BiomeModifications.addFeature(
				BiomeSelectors.foundInTheEnd().and(BiomeSelectors.excludeByKey(BiomeKeys.THE_END)),
				GenerationStep.Feature.VEGETAL_DECORATION,
				ModPlacedFeatures.ZENITH_SHROOM_PLACED_KEY
		);

		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(Potions.AWKWARD, ModItems.ZENITH_SHROOM, ModPotions.POTION_OF_LEVITATION);
		});
	}
}