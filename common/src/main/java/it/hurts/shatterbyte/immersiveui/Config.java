package it.hurts.shatterbyte.immersiveui;

import it.hurts.shatterbyte.shatterlib.module.config.ShatterConfig;
import it.hurts.shatterbyte.shatterlib.module.config.type.annotation.Comment;
import lombok.Data;

@Data
public class Config extends ShatterConfig {
    @Comment("Enables the hotbar selector animation.")
    private boolean enableHotbarSelectorAnimation = true;

    @Comment("Enables floating item rotation.")
    private boolean enableFloatingItemRotation = true;

    @Comment("Enables hovering of matching items.")
    private boolean enableMatchingItemHovering = true;

    @Comment("Disables vanilla slot highlighting")
    private boolean disableVanillaSlotHighlighting = true;
    
    @Comment("Enables particles for rare items.")
    private boolean enableRarityParticles = true;

    @Comment("Affects the speed of the hotbar selector.")
    private double hotbarSelectorSpeed = 3d;

    @Comment("Affects the size of the hovered item.")
    private float hoveredItemScale = 1.4f;

    @Comment("Affects the size of the floating item.")
    private float floatingItemScale = 1.4f;

    @Comment("Affects the hover amplitude of items, that match to the item that is carried in the cursor.")
    private float matchingItemHoverAmplitude = 0.8f;

    @Comment("Affects the easing speed, that is applied to the rotation of the floating item.")
    private float floatingItemEasingSpeed = 0.75f;

    @Comment("Affects the rotation amplitude of the floating item.")
    private float floatingItemRotationAmplitude = 1f;

    @Comment("Moves the hotbar selector above the items. You might want to disable this, if you use resource packs that change the default selector texture.")
    private boolean renderHotbarSelectorAboveItems = false;

//  ------------- Screen Shake -------------
    @Comment("Enables screen shake.")
    private boolean enableScreenShake = true;

    @Comment("Screen shake timer in ticks.")
    private int shakeTimer = 8;

    @Comment("Screen shake amplitude.")
    private float shakeAmplitude = 1.25f;

//  ------------- Enchant Options -------------
    @Comment("Enables particles in the enchant(ing/ment) table.")
    private boolean enableEnchantParticles = true;

    @Comment("Enables special formatting for items with cursed enchantments.")
    private boolean enableCurseFormatting = true;

//  ------------- Advancement Toasts -------------
    @Comment("Enables wobbly items in advancement toasts.")
    private boolean enableAdvancementToastItems = true;

    @Override
    public String getName() {
        return "immersiveui";
    }

    @Override
    public int getSchemaVersion() {
        return 1;
    }
}
