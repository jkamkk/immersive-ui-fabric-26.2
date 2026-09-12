package it.hurts.shatterbyte.immersiveui.compat;

import it.hurts.shatterbyte.immersiveui.client.MouseInfo;
import it.hurts.shatterbyte.immersiveui.client.RenderInfo;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.Random;

public interface ExtraScreenData {
    MouseInfo getMouseInfo();
    RenderInfo getRenderInfo();
    Random getRandom();
    Map<Slot, Float> getExpandingProgress();
    Map<Slot, it.hurts.shatterbyte.immersiveui.util.CommonCode.ReturnAnimation> getReturnAnimations();
    Map<Slot, Float> getMatchingHoverProgress();
    Map<Slot, ItemStack> getMatchingHoverStacks();
}
