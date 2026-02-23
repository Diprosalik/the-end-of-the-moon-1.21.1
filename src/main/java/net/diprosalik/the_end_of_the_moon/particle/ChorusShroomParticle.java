package net.diprosalik.the_end_of_the_moon.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ChorusShroomParticle extends SpriteBillboardParticle {
    public ChorusShroomParticle(ClientWorld clientWorld, double x, double y, double z,
                                SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed) {
        super(clientWorld, x, y, z, xSpeed, ySpeed, zSpeed);

        this.maxAge = 50;
        this.setSpriteForAge(spriteProvider);
        this.scale(0.8f);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }


        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ChorusShroomParticle(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
        }
    }

    @Override
    public int getBrightness(float tint) {
        // Erzeugt das "Emissive"-Leuchten im Dunkeln
        return 7340144; // Dies ist der interne Wert für volle Helligkeit (Lichtlevel 15)
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            // Wie im Firefly-Code: Nur selten (5% Chance) die Richtung ändern
            if (this.random.nextFloat() > 0.95F || this.age == 1) {
                // Ein sanfter neuer Impuls in eine zufällige Richtung
                this.velocityX = -0.02 + 0.04 * this.random.nextDouble();
                this.velocityY = -0.01 + 0.02 * this.random.nextDouble();
                this.velocityZ = -0.02 + 0.04 * this.random.nextDouble();
            }

            // Konstante Reibung, damit sie nicht wegschießen (entspricht friction im Firefly-Code)
            this.velocityX *= 0.96;
            this.velocityY *= 0.96;
            this.velocityZ *= 0.96;

            this.move(this.velocityX, this.velocityY, this.velocityZ);
        }

    }
}
