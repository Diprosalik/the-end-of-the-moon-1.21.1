package net.diprosalik.the_end_of_the_moon.particle;

import net.diprosalik.the_end_of_the_moon.TheEndOfTheMoon;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles{
    public static final SimpleParticleType CHORUS_SHROOM_PARTICLE =
            registerParticle("chorus_shroom_particle", FabricParticleTypes.simple());

    public static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(TheEndOfTheMoon.MOD_ID, name), particleType);
    }

}
