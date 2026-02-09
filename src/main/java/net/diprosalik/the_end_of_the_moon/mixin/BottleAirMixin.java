package net.diprosalik.the_end_of_the_moon.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.GlassBottleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlassBottleItem.class)
public class BottleAirMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void drinkAir(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);

        // Nur im Ende und wenn der Spieler weniger als die maximale Luft (300) hat
        if (!world.isClient && world.getRegistryKey() == World.END && user.getAir() < 300) {

            // Luft auffüllen (z.B. 60 Punkte = 3 Blasen / 3 Sekunden)
            int newAir = Math.min(user.getAir() + 120, 300);
            user.setAir(newAir);

            // Sound abspielen (Ein "Zischen" oder Schlucken)
            user.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, 0.5f, 1.2f);

            // Flasche verbrauchen (einfach das Item im Stack um 1 verringern)
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            // Erfolg zurückgeben, damit die Hand-Animation abgespielt wird
            cir.setReturnValue(TypedActionResult.consume(itemStack));
        }
    }
}