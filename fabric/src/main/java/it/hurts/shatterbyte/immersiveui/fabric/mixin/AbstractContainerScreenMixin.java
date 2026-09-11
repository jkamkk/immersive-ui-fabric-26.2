package it.hurts.shatterbyte.immersiveui.fabric.mixin;

import it.hurts.shatterbyte.immersiveui.compat.ExtraScreenData;
import it.hurts.shatterbyte.immersiveui.util.CommonCode;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {
    @Shadow
    @Nullable
    protected Slot hoveredSlot;

    @Inject(method = "extractSlot", at = @At("HEAD"), cancellable = true)
    public void renderReturnAnimation(GuiGraphicsExtractor guiGraphics, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
        if (CommonCode.renderReturnAnimation((AbstractContainerScreen<?>) (Object) this, guiGraphics, slot, ((ExtraScreenData) this).getReturnAnimations())) {
            ci.cancel();
        }
    }

    @Inject(method = "extractSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;item(Lnet/minecraft/world/item/ItemStack;III)V", shift = At.Shift.BEFORE))
    public void renderSize(GuiGraphicsExtractor guiGraphics, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
        guiGraphics.pose().pushMatrix();
        CommonCode.floatingRenderSize(guiGraphics, slot, hoveredSlot, ((ExtraScreenData) this).getExpandingProgress());
    }

    @Inject(method = "extractSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;fakeItem(Lnet/minecraft/world/item/ItemStack;III)V", shift = At.Shift.BEFORE))
    public void renderFakeSize(GuiGraphicsExtractor guiGraphics, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
        guiGraphics.pose().pushMatrix();
        CommonCode.floatingRenderSize(guiGraphics, slot, hoveredSlot, ((ExtraScreenData) this).getExpandingProgress());
    }

    @Inject(method = "extractSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;itemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", shift = At.Shift.AFTER))
    public void renderSizePopMatrix(GuiGraphicsExtractor guiGraphics, Slot slot, int mouseX, int mouseY, CallbackInfo ci) {
        guiGraphics.pose().popMatrix();
    }
}
