package it.hurts.shatterbyte.immersiveui.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.lang.reflect.Method;
import java.util.Map;

@Mixin(targets = "it.hurts.shatterbyte.shatterlib.client.config.widget.FieldWidget")
public class FieldWidgetLocalizationMixin {
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
            Map.entry("item transfer animation duration", "物品转移动画时长（毫秒）")
    );

    @ModifyArg(method = "extractWidgetRenderState", at = @At(value = "INVOKE", target = "Lit/hurts/shatterbyte/shatterlib/client/config/widget/FieldWidget;drawHighlightedString(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Ljava/lang/String;III)V"), index = 1)
    private String immersiveui$translateFieldName(String original) {
        return translate(original);
    }

    @Redirect(method = "wrapDescriptionLines", at = @At(value = "INVOKE", target = "Lit/hurts/shatterbyte/shatterlib/client/config/widget/GenericObjectWidget$FieldInfo;description()Ljava/lang/String;"))
    private String immersiveui$translateDescription(Object fieldInfo) {
        try {
            Method method = fieldInfo.getClass().getMethod("description");
            return translate((String) method.invoke(fieldInfo));
        } catch (ReflectiveOperationException exception) {
            return "";
        }
    }

    private static String translate(String original) {
        if (!"zh_cn".equals(Minecraft.getInstance().options.languageCode)) {
            return original;
        }

        return TRANSLATIONS.getOrDefault(original, original);
    }
}
