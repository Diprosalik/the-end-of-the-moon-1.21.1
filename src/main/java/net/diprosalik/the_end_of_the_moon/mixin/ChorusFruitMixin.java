package net.diprosalik.the_end_of_the_moon.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ChorusFruitItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChorusFruitItem.class)
public class ChorusFruitMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void addOxygenOnEat(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        if (!world.isClient && user instanceof PlayerEntity player) {

            int currentAir = player.getAir();
            int maxAir = player.getMaxAir();

            player.setAir(Math.min(currentAir + 30, maxAir));
        }
    }
}