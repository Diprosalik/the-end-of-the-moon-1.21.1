package net.diprosalik.the_end_of_the_moon.item;

import net.diprosalik.the_end_of_the_moon.TheEndOfTheMoon;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.PotionItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item CHORUS_FRUIT_GAS_BOTTLE = registerItem("chorus_fruit_gas_bottle",
            new PotionItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(TheEndOfTheMoon.MOD_ID, name), item);
    }

//    private static void itemGroupIngredients(FabricItemGroupEntries entries) {
//
//    }

    public static void registerModItems() {
        TheEndOfTheMoon.LOGGER.info("Registering Mod Items for " + TheEndOfTheMoon.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(CHORUS_FRUIT_GAS_BOTTLE);
        });
    }
}
