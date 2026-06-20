package seal.thelinuxseal.sealymod.client.config.screens;

import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;

public class GeneralConfigScreen {
    // Changed name to 'get' and added 'static' for clean access
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.general.title"))
                .build();
    }
}