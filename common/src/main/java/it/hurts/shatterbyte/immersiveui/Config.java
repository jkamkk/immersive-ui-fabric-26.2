package it.hurts.shatterbyte.immersiveui;

import it.hurts.shatterbyte.shatterlib.module.config.ShatterConfig;
import it.hurts.shatterbyte.shatterlib.module.config.type.annotation.Comment;
import lombok.Data;

@Data
public class Config extends ShatterConfig {
    @Comment("启用快捷栏选择框动画。")
    private boolean enableHotbarSelectorAnimation = true;

    @Comment("启用鼠标携带物品的旋转动画。")
    private boolean enableFloatingItemRotation = true;

    @Comment("启用与鼠标携带物品相同的物品悬浮效果。")
    private boolean enableMatchingItemHovering = true;

    @Comment("禁用原版槽位高亮。")
    private boolean disableVanillaSlotHighlighting = true;
    
    @Comment("启用稀有物品粒子效果。")
    private boolean enableRarityParticles = true;

    @Comment("调整快捷栏选择框动画速度。")
    private double hotbarSelectorSpeed = 3d;

    @Comment("调整悬停物品的放大比例。")
    private float hoveredItemScale = 1.4f;

    @Comment("调整鼠标携带物品的显示比例。")
    private float floatingItemScale = 1.4f;

    @Comment("调整与鼠标携带物品相同的物品的悬浮幅度。")
    private float matchingItemHoverAmplitude = 0.8f;

    @Comment("调整鼠标携带物品旋转的缓动速度。")
    private float floatingItemEasingSpeed = 0.75f;

    @Comment("调整鼠标携带物品的旋转幅度。")
    private float floatingItemRotationAmplitude = 1f;

    @Comment("将快捷栏选择框绘制在物品上方。若资源包修改了原版选择框纹理，可关闭此项。")
    private boolean renderHotbarSelectorAboveItems = false;

//  ------------- Screen Shake -------------
    @Comment("启用界面震动。")
    private boolean enableScreenShake = true;

    @Comment("界面震动持续时间，单位为 tick。")
    private int shakeTimer = 8;

    @Comment("界面震动幅度。")
    private float shakeAmplitude = 1.25f;

//  ------------- Enchant Options -------------
    @Comment("启用附魔台粒子效果。")
    private boolean enableEnchantParticles = true;

    @Comment("启用诅咒附魔的特殊文字效果。")
    private boolean enableCurseFormatting = true;

//  ------------- Advancement Toasts -------------
    @Comment("启用进度提示中的物品摇摆动画。")
    private boolean enableAdvancementToastItems = true;

//  ------------- Item Transfer Options -------------
    @Comment("启用从槽位拿起物品的动画。")
    private boolean enableItemPickupAnimation = true;

    @Comment("启用将物品放回槽位的动画。")
    private boolean enableItemReturnAnimation = true;

    @Comment("拿起和放回动画的持续时间，单位为毫秒。")
    private int itemTransferAnimationDuration = 140;

    @Override
    public String getName() {
        return "immersiveui";
    }

    @Override
    public int getSchemaVersion() {
        return 1;
    }
}
