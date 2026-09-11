package it.hurts.shatterbyte.immersiveui.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(targets = "it.hurts.shatterbyte.shatterlib.client.config.widget.GenericObjectWidget$FieldInfo")
public class FieldInfoLocalizationMixin {
    private static final Map<String, String> TRANSLATIONS = Map.ofEntries(
            Map.entry("enableHotbarSelectorAnimation", "启用快捷栏选择框动画"),
            Map.entry("enableFloatingItemRotation", "启用鼠标携带物品的旋转动画"),
            Map.entry("enableMatchingItemHovering", "启用相同物品悬浮效果"),
            Map.entry("disableVanillaSlotHighlighting", "禁用原版槽位高亮"),
            Map.entry("enableRarityParticles", "启用稀有物品粒子效果"),
            Map.entry("hotbarSelectorSpeed", "快捷栏选择框动画速度"),
            Map.entry("hoveredItemScale", "悬停物品缩放比例"),
            Map.entry("floatingItemScale", "鼠标携带物品缩放比例"),
            Map.entry("matchingItemHoverAmplitude", "相同物品悬浮幅度"),
            Map.entry("floatingItemEasingSpeed", "携带物品旋转缓动速度"),
            Map.entry("floatingItemRotationAmplitude", "携带物品旋转幅度"),
            Map.entry("renderHotbarSelectorAboveItems", "将快捷栏选择框绘制在物品上方"),
            Map.entry("enableScreenShake", "启用界面震动"),
            Map.entry("shakeTimer", "界面震动持续时间（tick）"),
            Map.entry("shakeAmplitude", "界面震动幅度"),
            Map.entry("enableEnchantParticles", "启用附魔台粒子效果"),
            Map.entry("enableCurseFormatting", "启用诅咒附魔特殊文字效果"),
            Map.entry("enableAdvancementToastItems", "启用进度提示物品摇摆动画"),
            Map.entry("enableItemPickupAnimation", "启用拿起物品动画"),
            Map.entry("enableItemReturnAnimation", "启用放回物品动画"),
            Map.entry("itemTransferAnimationDuration", "物品转移动画时长（毫秒）")
    );

    @ModifyReturnValue(method = "name", at = @At("RETURN"))
    private String immersiveui$translateName(String original) {
        return translate(original);
    }

    @ModifyReturnValue(method = "description", at = @At("RETURN"))
    private String immersiveui$translateDescription(String original) {
        return translate(original);
    }

    private static String translate(String original) {
        if (!"zh_cn".equals(Minecraft.getInstance().options.languageCode)) {
            return original;
        }

        return TRANSLATIONS.getOrDefault(original, original);
    }
}
