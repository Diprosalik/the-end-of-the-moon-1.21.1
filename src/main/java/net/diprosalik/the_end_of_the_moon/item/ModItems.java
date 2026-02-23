package net.diprosalik.the_end_of_the_moon.item;

import net.diprosalik.the_end_of_the_moon.TheEndOfTheMoon;
import net.diprosalik.the_end_of_the_moon.block.ModBlock;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class ModItems {

    public static final Item CHORUS_FRUIT_GAS_BOTTLE = registerItem("chorus_fruit_gas_bottle",
            new Item(new Item.Settings()
                    .maxCount(16)
                    .food(new FoodComponent.Builder()
                            .alwaysEdible()
                            .snack()
                            .build())
            ) {
                @Override
                public UseAction getUseAction(ItemStack stack) {
                    return UseAction.DRINK;
                }

                @Override
                public SoundEvent getDrinkSound() {
                    return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
                }

                @Override
                public SoundEvent getEatSound() {
                    return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
                }

                @Override
                public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
                    if (!world.isClient && user instanceof PlayerEntity player) {
                        player.setAir(300);

                        if (!player.getAbilities().creativeMode) {
                            if (stack.isEmpty()) {
                                return new ItemStack(Items.GLASS_BOTTLE);
                            }
                            player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
                        }
                    }
                    return super.finishUsing(stack, world, user);
                }
            });

    public static final Item CHORUS_SHROOM = registerItem("chorus_shroom",
            new AliasedBlockItem(ModBlock.CHORUS_SHROOM, new Item.Settings().maxCount(64)));

    public static final Item ZENITH_SHROOM = registerItem("zenith_shroom",
            new AliasedBlockItem(ModBlock.ZENITH_SHROOM, new Item.Settings().maxCount(64)));

    public static final Item CHORUS_SEEDS = registerItem("chorus_seeds",
            new AliasedBlockItem(ModBlock.CHORUS_ROOTLING, new Item.Settings().maxCount(64)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TheEndOfTheMoon.MOD_ID, name), item);
    }

    public static void registerModItems() {
        TheEndOfTheMoon.LOGGER.info("Registering Mod Items for " + TheEndOfTheMoon.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.addAfter(Items.HONEY_BOTTLE, CHORUS_FRUIT_GAS_BOTTLE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.NETHER_WART, CHORUS_SEEDS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.WARPED_FUNGUS, CHORUS_SHROOM);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(ModItems.CHORUS_SHROOM, ZENITH_SHROOM);
        });
    }
}
