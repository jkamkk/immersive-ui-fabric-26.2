package it.hurts.shatterbyte.immersiveui.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import it.hurts.shatterbyte.immersiveui.ImmersiveUI;
import it.hurts.shatterbyte.shatterlib.client.config.MultipleConfigScreen;

public final class ImmersiveUIModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> new MultipleConfigScreen(ImmersiveUI.MOD_ID, screen);
    }
}
