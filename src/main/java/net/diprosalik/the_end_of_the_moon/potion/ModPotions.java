package net.diprosalik.the_end_of_the_moon.potion;

import net.diprosalik.the_end_of_the_moon.TheEndOfTheMoon;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.listener.GameEventListener;

public class ModPotions {
    public static final RegistryEntry<Potion> POTION_OF_LEVITATION = registerPotion("potion_of_levitation",
            new Potion(new StatusEffectInstance(StatusEffects.LEVITATION, 400, 0)));


    private static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Identifier.of(TheEndOfTheMoon.MOD_ID, name), potion);
    }

    public static void registerPotions() {
        TheEndOfTheMoon.LOGGER.info("Registering Mod Potions for " + TheEndOfTheMoon.MOD_ID);
    }
}
