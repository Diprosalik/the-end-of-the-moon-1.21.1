package net.diprosalik.the_end_of_the_moon.mixin.conduit;

import net.minecraft.client.render.block.entity.ConduitBlockEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConduitBlockEntityRenderer.class)
public class ConduitRendererMixin {

    @Unique
    private static final SpriteIdentifier CUSTOM_CLOSED_EYE = new SpriteIdentifier(
            SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE,
            Identifier.of("the_end_of_the_moon", "entity/conduit/closed_eye")
    );


    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructor(BlockEntityRendererFactory.Context ctx, CallbackInfo ci) {

    }

    @Inject(method = "render", at = @At("HEAD"))
    private void overrideRender(CallbackInfo ci) {

    }
}