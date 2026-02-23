package net.diprosalik.the_end_of_the_moon;

import net.diprosalik.the_end_of_the_moon.particle.ChorusShroomParticle;
import net.diprosalik.the_end_of_the_moon.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public class TheEndOfTheMoonClient implements ClientModInitializer {

    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.CHORUS_SHROOM_PARTICLE, ChorusShroomParticle.Factory::new);
    }
}
