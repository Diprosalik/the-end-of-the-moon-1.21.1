package net.diprosalik.the_end_of_the_moon.mixin;

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

    // Wir nutzen den Konstruktor des Renderers (ähnlich wie dein Creeper-Beispiel)
    @Inject(method = "<init>", at = @At("TAIL"))
    private void onConstructor(BlockEntityRendererFactory.Context ctx, CallbackInfo ci) {
        // Hier könnten wir Logik ausführen, wenn der Renderer erstellt wird.
        // Da die Textur-Felder im ConduitRenderer leider 'static final' sind,
        // ist der direkte Tausch hier ohne Shadow schwierig.
    }

    // Alternative: Wir überschreiben die Methode, die die Textur für das Modell auswählt
    // Das ist oft sicherer als Variablen-Manipulation
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void overrideRender(CallbackInfo ci) {
        // Wenn dieser Inject kompiliert, haben wir einen stabilen Ankerpunkt.
    }
}