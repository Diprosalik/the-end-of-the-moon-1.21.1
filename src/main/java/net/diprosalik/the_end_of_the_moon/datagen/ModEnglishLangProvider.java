package net.diprosalik.the_end_of_the_moon.datagen;

import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.diprosalik.the_end_of_the_moon.item.ModItems;
import net.diprosalik.the_end_of_the_moon.potion.ModPotions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import java.util.concurrent.CompletableFuture;

public class ModEnglishLangProvider extends FabricLanguageProvider {
    public ModEnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.CHORUS_FRUIT_GAS_BOTTLE, "Chorus Fruit Gas Bottle");
        translationBuilder.add(ModItems.CHORUS_SEEDS, "Chorus Seeds");
        translationBuilder.add(ModItems.ChorusRootlingBlock, "Chorus Rootling");
        translationBuilder.add(ModItems.CHORUS_SHROOM, "Chorus Shroom");
        translationBuilder.add(ModItems.ZENITH_SHROOM, "Zenith Shroom");

        translationBuilder.add("item.minecraft.potion.effect.potion_of_levitation", "Potion of Levitation");
        translationBuilder.add("item.minecraft.splash_potion.effect.potion_of_levitation", "Splash Potion of Levitation");
        translationBuilder.add("item.minecraft.lingering_potion.effect.potion_of_levitation", "Lingering Potion of Levitation");
        translationBuilder.add("item.minecraft.tipped_arrow.effect.potion_of_levitation", "Arrow of Levitation");
    }
}