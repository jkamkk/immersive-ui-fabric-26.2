package it.hurts.shatterbyte.immersiveui.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(targets = "it.hurts.shatterbyte.shatterlib.client.config.widget.GenericObjectWidget$FieldInfo")
public class FieldInfoLocalizationMixin {
    private static final Map<String, String> TRANSLATIONS = Map.ofEntries(
            Map.entry("enable hotbar selector animation", "启用快捷栏选择框动画"),
            Map.entry("enable floating item rotation", "启用鼠标携带物品的旋转动画"),
            Map.entry("enable matching item hovering", "启用相同物品悬浮效果"),
            Map.entry("disable vanilla slot highlighting", "禁用原版槽位高亮"),
            Map.entry("enable rarity particles", "启用稀有物品粒子效果"),
            Map.entry("hotbar selector speed", "快捷栏选择框动画速度"),
            Map.entry("hovered item scale", "悬停物品缩放比例"),
            Map.entry("floating item scale", "鼠标携带物品缩放比例"),
            Map.entry("matching item hover amplitude", "相同物品悬浮幅度"),
            Map.entry("floating item easing speed", "携带物品旋转缓动速度"),
            Map.entry("floating item rotation amplitude", "携带物品旋转幅度"),
            Map.entry("render hotbar selector above items", "将快捷栏选择框绘制在物品上方"),
            Map.entry("enable screen shake", "启用界面震动"),
            Map.entry("shake timer", "界面震动持续时间（tick）"),
            Map.entry("shake amplitude", "界面震动幅度"),
            Map.entry("enable enchant particles", "启用附魔台粒子效果"),
            Map.entry("enable curse formatting", "启用诅咒附魔特殊文字效果"),
            Map.entry("enable advancement toast items", "启用进度提示物品摇摆动画"),
            Map.entry("enable item pickup animation", "启用拿起物品动画"),
            Map.entry("enable item return animation", "启用放回物品动画"),
            Map.entry("item transfer animation duration", "物品转移动画时长（毫秒）"),
            Map.entry("enables the hotbar selector animation.", "启用快捷栏选择框动画。"),
            Map.entry("enables floating item rotation.", "启用鼠标携带物品的旋转动画。"),
            Map.entry("enables hovering of matching items.", "启用相同物品悬浮效果。"),
            Map.entry("disables vanilla slot highlighting", "禁用原版槽位高亮。"),
            Map.entry("enables particles for rare items.", "启用稀有物品粒子效果。"),
            Map.entry("affects the speed of the hotbar selector.", "调整快捷栏选择框动画速度。"),
            Map.entry("affects the size of the hovered item.", "调整悬停物品的缩放比例。"),
            Map.entry("affects the size of the floating item.", "调整鼠标携带物品的缩放比例。"),
            Map.entry("enables screen shake.", "启用界面震动。"),
            Map.entry("enables particles in the enchant(ing/ment) table.", "启用附魔台粒子效果。"),
            Map.entry("enables special formatting for items with cursed enchantments.", "启用诅咒附魔特殊文字效果。"),
            Map.entry("enables wobbly items in advancement toasts.", "启用进度提示物品摇摆动画。"),
            Map.entry("enables the animation when picking an item up from a slot.", "启用从槽位拿起物品的动画。"),
            Map.entry("enables the animation when returning an item to a slot.", "启用将物品放回槽位的动画。"),
            Map.entry("duration of item pickup and return animations in milliseconds.", "拿起和放回动画的持续时间，单位为毫秒。")
    );

    @Inject(method = "name", at = @At("RETURN"), cancellable = true)
    private void immersiveui$translateName(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(translate(cir.getReturnValue()));
    }

    @Inject(method = "description", at = @At("RETURN"), cancellable = true)
    private void immersiveui$translateDescription(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(translate(cir.getReturnValue()));
    }

    private static String translate(String original) {
        if (!"zh_cn".equals(Minecraft.getInstance().options.languageCode)) {
            return original;
        }

        return TRANSLATIONS.getOrDefault(original.toLowerCase(java.util.Locale.ROOT), original);
    }
}
