package it.hurts.shatterbyte.immersiveui.util;

import it.hurts.shatterbyte.immersiveui.ImmersiveUI;
import it.hurts.shatterbyte.immersiveui.client.MouseInfo;
import it.hurts.shatterbyte.immersiveui.client.RenderInfo;
import it.hurts.shatterbyte.immersiveui.client.VariableStorage;
import it.hurts.shatterbyte.immersiveui.client.particle.FlameUIParticle;
import it.hurts.shatterbyte.immersiveui.client.particle.RarityUIParticle;
import it.hurts.shatterbyte.immersiveui.mixin.AbstractContainerScreenAccessor;
import it.hurts.shatterbyte.shatterlib.ShatterLibClient;
import it.hurts.shatterbyte.shatterlib.client.animation.easing.EaseType;
import it.hurts.shatterbyte.shatterlib.client.animation.easing.TransitionType;
import it.hurts.shatterbyte.shatterlib.client.particle.UIParticle;
import it.hurts.shatterbyte.shatterlib.util.AnimationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static it.hurts.shatterbyte.immersiveui.client.VariableStorage.*;

public class CommonCode {
    public static final class ReturnAnimation {
        private final ItemStack stack;
        private final float startX;
        private final float startY;
        private final float endX;
        private final float endY;
        private final long startTime;

        public ReturnAnimation(ItemStack stack, float startX, float startY, float endX, float endY) {
            this.stack = stack;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.startTime = System.nanoTime();
        }

        private float progress() {
            long durationNs = Math.max(1, ImmersiveUI.CONFIG.getItemTransferAnimationDuration()) * 1_000_000L;
            return Mth.clamp((float) (System.nanoTime() - startTime) / durationNs, 0f, 1f);
        }
    }

    public static ItemStack updateReturnAnimations(AbstractContainerScreen<?> screen, int mouseX, int mouseY,
                                                    Map<Slot, ItemStack> previousSlotItems,
                                                    Map<Slot, ReturnAnimation> returnAnimations,
                                                    ItemStack previousCarried,
                                                    boolean quickCrafting) {
        if (quickCrafting) {
            // During left/right-button drag placement, slots are filled while the
            // cursor still carries the source stack. Those are placements, not returns.
            returnAnimations.clear();
        }

        for (Slot slot : screen.getMenu().slots) {
            ItemStack current = slot.getItem();
            ItemStack previous = previousSlotItems.getOrDefault(slot, ItemStack.EMPTY);

            if (!quickCrafting
                    && ImmersiveUI.CONFIG.isEnableItemReturnAnimation()
                    && !previousCarried.isEmpty()
                    && !current.isEmpty()
                    && ItemStack.isSameItemSameComponents(current, previousCarried)
                    && (previous.isEmpty() || !ItemStack.isSameItemSameComponents(previous, current))) {
                AbstractContainerScreenAccessor accessor = (AbstractContainerScreenAccessor) screen;
                float startX = mouseX - accessor.getLeftPos() - 8;
                float startY = mouseY - accessor.getTopPos() - 8;
                returnAnimations.put(slot, new ReturnAnimation(current.copy(), startX, startY, slot.x, slot.y));
            }

            if (current.isEmpty()) {
                previousSlotItems.remove(slot);
            } else {
                previousSlotItems.put(slot, current.copy());
            }
        }

        return screen.getMenu().getCarried().copy();
    }

    public static ReturnAnimation createPickupAnimation(AbstractContainerScreen<?> screen, int mouseX, int mouseY,
                                                        Map<Slot, ItemStack> previousSlotItems, ItemStack previousCarried) {
        ItemStack currentCarried = screen.getMenu().getCarried();
        if (!ImmersiveUI.CONFIG.isEnableItemPickupAnimation()
                || !previousCarried.isEmpty() || currentCarried.isEmpty()) {
            return null;
        }

        AbstractContainerScreenAccessor accessor = (AbstractContainerScreenAccessor) screen;
        for (Slot slot : screen.getMenu().slots) {
            ItemStack previous = previousSlotItems.getOrDefault(slot, ItemStack.EMPTY);
            if (!previous.isEmpty() && slot.getItem().isEmpty()
                    && ItemStack.isSameItemSameComponents(previous, currentCarried)) {
                float endX = mouseX - 8;
                float endY = mouseY - 8;
                return new ReturnAnimation(
                        previous.copy(),
                        accessor.getLeftPos() + slot.x,
                        accessor.getTopPos() + slot.y,
                        endX,
                        endY
                );
            }
        }

        return null;
    }

    public static boolean renderReturnAnimation(AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics,
                                                Slot slot, Map<Slot, ReturnAnimation> returnAnimations) {
        ReturnAnimation animation = returnAnimations.get(slot);
        if (animation == null) {
            return false;
        }

        float progress = animation.progress();
        if (progress >= 1f) {
            returnAnimations.remove(slot);
            return false;
        }

        float eased = (float) TransitionType.QUAD.apply(EaseType.EASE_OUT, progress);
        float x = Mth.lerp(eased, animation.startX, animation.endX);
        float y = Mth.lerp(eased, animation.startY, animation.endY);

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(x + 8, y + 8);
        if (slot.isFake()) {
            guiGraphics.fakeItem(animation.stack, -8, -8);
        } else {
            guiGraphics.item(animation.stack, -8, -8);
        }
        guiGraphics.itemDecorations(Minecraft.getInstance().font, animation.stack, -8, -8);
        guiGraphics.pose().popMatrix();
        return true;
    }

    public static boolean renderPickupAnimation(GuiGraphicsExtractor guiGraphics, ReturnAnimation animation) {
        float progress = animation.progress();
        if (progress >= 1f) {
            return false;
        }

        float eased = (float) TransitionType.QUAD.apply(EaseType.EASE_OUT, progress);
        float x = Mth.lerp(eased, animation.startX, animation.endX);
        float y = Mth.lerp(eased, animation.startY, animation.endY);
        float scale = Mth.lerp(eased, 1f, ImmersiveUI.CONFIG.getFloatingItemScale());

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(x + 8, y + 8);
        guiGraphics.pose().scale(scale, scale);
        guiGraphics.item(animation.stack, -8, -8);
        guiGraphics.itemDecorations(Minecraft.getInstance().font, animation.stack, -8, -8);
        guiGraphics.pose().popMatrix();
        return true;
    }

    public static void gooeyRenderCode(float partialTick) {
        currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - VariableStorage.lastExecutedTime;
        if (elapsedTime >= TARGET_INTERVAL_MS) {
            lastExecutedTime = currentTime;
        }
    }

    public static void renderFurnaceParticles(Screen screen, Slot fuelSlot, boolean burstCondition, boolean isLit, AtomicBoolean shouldBurst) {
        if (!isLit) return;
        Random random = new Random();

        if (burstCondition && !shouldBurst.get()) {
            shouldBurst.set(true);

            AbstractContainerScreenAccessor accessor = (AbstractContainerScreenAccessor) screen;
            int leftPos = accessor.getLeftPos();
            int topPos = accessor.getTopPos();

            for (int ii = 0; ii < 8; ii++) {
                FlameUIParticle particle = new FlameUIParticle(leftPos+fuelSlot.x+8+random.nextInt(-6,6), topPos+fuelSlot.y+10+random.nextInt(-6,6), random.nextInt(16,24));
                particle.setScreen(screen);
                particle.instantiate();
            }
        }

        if (!burstCondition && shouldBurst.get()) {
            shouldBurst.set(false);
        }
    }

    public static void renderFloating(Screen screen, GuiGraphicsExtractor guiGraphics, MouseInfo mouseInfo, int i, int j, ItemStack itemStack, Random random, RenderInfo renderInfo, String string, CallbackInfo ci) {
        float scale = ImmersiveUI.CONFIG.getFloatingItemScale();
        float deltaTime = (float) ShatterLibClient.getDeltaTime()*20;
        float amplitude = ImmersiveUI.CONFIG.getFloatingItemRotationAmplitude();

        if (mouseInfo.oX != Integer.MIN_VALUE && mouseInfo.oY != Integer.MIN_VALUE) { // Only calculate if previous values are set
            renderInfo.targetAngle = Mth.clamp(-mouseInfo.deltaX / 8f * amplitude, -Mth.HALF_PI/(2/amplitude), Mth.HALF_PI/(2/amplitude));

            // Update velocities based on change in target positions
            renderInfo.currentAngleVelocity += (renderInfo.targetAngle - renderInfo.currentAngle) * renderInfo.easingSpeed * deltaTime;
        }

        //currentAngleVelocity = Mth.clamp(currentAngleVelocity,-0.5f,0.5f);

        // Apply velocities to current angles
        renderInfo.currentAngle = Mth.clamp(renderInfo.currentAngle + renderInfo.currentAngleVelocity * deltaTime, -Mth.HALF_PI/(2/amplitude), Mth.HALF_PI/(2/amplitude));

        // Apply damping to velocities
        renderInfo.currentAngleVelocity = renderInfo.currentAngleVelocity * (float) Math.pow(renderInfo.inertiaDamping, deltaTime);

//        guiGraphics.pose().pushPose();
//        guiGraphics.pose().translate(-300, -100, 0);
//        guiGraphics.pose().scale(0.5f, 0.5f, 1f);
//        guiGraphics.drawString(Minecraft.getInstance().font, itemStack.getDisplayName().toString(), 2, 2, 0xffffff, true);
//        guiGraphics.drawString(Minecraft.getInstance().font, itemStack.getHoverName().toString(), 2, 11, 0xffffff, true);
//        guiGraphics.pose().popPose();

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(i + 8, j + 8);
        Matrix3x2f matrix = new Matrix3x2f(guiGraphics.pose());
        guiGraphics.pose().scale(scale, scale);
        if (ImmersiveUI.CONFIG.isEnableFloatingItemRotation()) guiGraphics.pose().rotate(Mth.abs(renderInfo.currentAngle) > 0.01f ? renderInfo.currentAngle : 0f);
        guiGraphics.item(itemStack, -8, -8);

        if (ImmersiveUI.CONFIG.isEnableRarityParticles()) {
            List<Integer> colors = itemStack.getHoverName().getSiblings().stream()
                    .map(component -> component.getStyle().getColor())
                    .filter(Objects::nonNull)
                    .map(colorValue -> colorValue.getValue())
                    .toList();
            int color = colors.isEmpty() ? 0xffffff : colors.get(random.nextInt(colors.size()));
            if (colors.isEmpty() && itemStack.getHoverName().getStyle().getColor() != null) {
                color = itemStack.getHoverName().getStyle().getColor().getValue();
            }
            if (colors.isEmpty() && itemStack.getDisplayName().getStyle().getColor() != null) {
                color = itemStack.getDisplayName().getStyle().getColor().getValue();
            }

            if (color != 0xffffff) {
                if (Mth.abs(mouseInfo.deltaX) > 0f || Mth.abs(mouseInfo.deltaY) > 0) {
                    Vector2f direction = new Vector2f(mouseInfo.deltaX, mouseInfo.deltaY);
                    UIParticle particle = new RarityUIParticle(
                            random.nextFloat(0.5f, 0.625f)*direction.length(),
                            random.nextInt(12, 20),
                            random.nextFloat(-4,4),
                            random.nextFloat(-4,4),
                            -direction.x,
                            -direction.y,
                            random.nextFloat(-10, 10),
                            color,
                            UIParticle.Layer.SCREEN,
                            233f
                    );
                    particle.setMatrix(matrix);
                    particle.setScreen(screen);
                    particle.instantiate();
                }
            }
        }

        Font font = Minecraft.getInstance().font;
        guiGraphics.itemDecorations(font, itemStack, -8, -8, string);
        //guiGraphics.drawString(font, expandingProgress.values().toString(), 0, 0, 0xFFFFFF, true);
        guiGraphics.pose().popMatrix();

        ci.cancel();
    }

    public static void shakeScreen(GuiGraphicsExtractor guiGraphics, Screen screen, AtomicReference<Float> timer, float durationMultiplier) {
        boolean shouldShake = shakeScreen.contains(screen);
        if (shouldShake) {
            shakeScreen.remove(screen);
            timer.set((float) ImmersiveUI.CONFIG.getShakeTimer() * durationMultiplier);
        }
        if (!ImmersiveUI.CONFIG.isEnableScreenShake()) return;

        if (timer.get() > 0) {
            Random rand = new Random();
            timer.set((float) Mth.clamp(timer.get()-ShatterLibClient.getDeltaTime()*20f, 0, ImmersiveUI.CONFIG.getShakeTimer()));
            Vector2f shakeDirection = new Vector2f(rand.nextFloat(-1, 1), rand.nextFloat(-1, 1)).normalize(ImmersiveUI.CONFIG.getShakeAmplitude());
            guiGraphics.pose().translate(shakeDirection.x*(timer.get()/ImmersiveUI.CONFIG.getShakeTimer()), shakeDirection.y*(timer.get()/ImmersiveUI.CONFIG.getShakeTimer()));
        }
    }

    public static void floatingRenderSize(GuiGraphicsExtractor guiGraphics, Slot slot, Slot hoveredSlot, Map<Slot, Float> expandingProgress) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null || slot == null)
            return;

        ItemStack carried = player.containerMenu.getCarried();
        if (!carried.isEmpty() && ItemStack.isSameItemSameComponents(slot.getItem(), carried) && ImmersiveUI.CONFIG.isEnableMatchingItemHovering()) {
            guiGraphics.pose().translate(Mth.sin(Minecraft.getInstance().player.tickCount*0.215f + Objects.hash(slot.x, slot.y))*ImmersiveUI.CONFIG.getMatchingItemHoverAmplitude(), Mth.cos(Minecraft.getInstance().player.tickCount*0.13f + Objects.hash(slot.x, slot.y))*ImmersiveUI.CONFIG.getMatchingItemHoverAmplitude());
        }

        boolean hovering = hoveredSlot == slot && (carried.isEmpty() || ItemStack.isSameItemSameComponents(slot.getItem(), carried));
        float deltaTime = (float) (ShatterLibClient.getDeltaTime() * 4f);

        expandingProgress.put(slot, Mth.clamp(expandingProgress.getOrDefault(slot, 0f) + deltaTime * (hovering ? 1 : -1), 0, 1f));

        float p = expandingProgress.get(slot);
        float progress = AnimationUtils.lerp(1f, ImmersiveUI.CONFIG.getHoveredItemScale(), TransitionType.QUAD.apply(EaseType.EASE_OUT, p));

        guiGraphics.pose().translate(slot.x + 8, slot.y + 8);
        guiGraphics.pose().scale(progress, progress);
        guiGraphics.pose().translate(-slot.x - 8, -slot.y - 8);
    }

    public static void computeMouseDelta(MouseInfo mouseInfo, int mouseX, int mouseY) {
        if (mouseInfo.oX == Integer.MIN_VALUE || mouseInfo.oY == Integer.MIN_VALUE) {
            mouseInfo.deltaX = 0f;
            mouseInfo.deltaY = 0f;
            mouseInfo.oX = mouseX;
            mouseInfo.oY = mouseY;
            return;
        }

        double deltaTime = ShatterLibClient.getDeltaTime() * 100f;
        mouseInfo.deltaX = (float) ((mouseInfo.oX - mouseX) / deltaTime);
        mouseInfo.deltaY = (float) ((mouseInfo.oY - mouseY) / deltaTime);
        mouseInfo.oX = mouseX;
        mouseInfo.oY = mouseY;
    }

//    public static void resetOldMousePosition(MouseInfo mouseInfo, int mouseX, int mouseY) {
//
//    }
}
