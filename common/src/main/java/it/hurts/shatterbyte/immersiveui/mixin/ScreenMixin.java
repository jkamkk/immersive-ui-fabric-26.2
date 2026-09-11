package it.hurts.shatterbyte.immersiveui.mixin;

import it.hurts.shatterbyte.immersiveui.compat.ExtraScreenData;
import it.hurts.shatterbyte.immersiveui.util.CommonCode;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "extractRenderStateWithTooltipAndSubtitles", at = @At("HEAD"))
    private void immersiveui$trackMouse(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if ((Object) this instanceof ExtraScreenData screenData) {
            CommonCode.computeMouseDelta(screenData.getMouseInfo(), mouseX, mouseY);
        }
    }

//    @Inject(method = "onClose", at = @At("TAIL"))
//    public void clearScreenParticles(CallbackInfo ci) {
//        ParticleStorage.EMITTERS.clear();
//    }
}
