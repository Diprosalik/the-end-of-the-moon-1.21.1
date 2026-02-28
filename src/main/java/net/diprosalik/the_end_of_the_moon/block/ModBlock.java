package net.diprosalik.the_end_of_the_moon.block;

import net.diprosalik.the_end_of_the_moon.*;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlock {

    public static final Block CHORUS_ROOTLING = new ChorusRootlingBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_PURPLE)
                    .ticksRandomly() // Wichtig für die Verwandlung zur Blume!
                    .strength(0.4F)
                    .sounds(BlockSoundGroup.WART_BLOCK)
                    .nonOpaque()
    );

    public static final Block CHORUS_SHROOM = new ChorusShroomBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.PALE_PURPLE)
                    .strength(0.1F) // Sehr leicht zu zerbrechen
                    .sounds(BlockSoundGroup.GRASS)
                    .nonOpaque()
                    .breakInstantly()
    );

    public static final Block POTTED_CHORUS_SHROOM = new PottedChorusShroomBlock(
            CHORUS_SHROOM,
            AbstractBlock.Settings.copy(Blocks.POTTED_AZALEA_BUSH).nonOpaque()
    );

    public static final Block ZENITH_SHROOM = new ZenithShroomBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.WHITE_GRAY)
                    .strength(0.1F)
                    .sounds(BlockSoundGroup.GRASS)
                    .nonOpaque()
                    .breakInstantly()
    );

    public static final Block POTTED_ZENITH_SHROOM = new PottedZenithShroomBlock(
            ZENITH_SHROOM,
            AbstractBlock.Settings.copy(Blocks.POTTED_AZALEA_BUSH).nonOpaque()
    );

    // Hilfsmethode zur Registrierung
    public static void registerModBlocks() {
        Registry.register(Registries.BLOCK, Identifier.of("the-end-of-the-moon", "chorus_rootling"), CHORUS_ROOTLING);
        Registry.register(Registries.BLOCK, Identifier.of("the-end-of-the-moon", "chorus_shroom"), CHORUS_SHROOM);
        Registry.register(Registries.BLOCK, Identifier.of("the-end-of-the-moon", "zenith_shroom"), ZENITH_SHROOM);
        Registry.register(Registries.BLOCK, Identifier.of("the-end-of-the-moon", "potted_chorus_shroom"), POTTED_CHORUS_SHROOM);
        Registry.register(Registries.BLOCK, Identifier.of("the-end-of-the-moon", "potted_zenith_shroom"), POTTED_ZENITH_SHROOM);
    }
}
