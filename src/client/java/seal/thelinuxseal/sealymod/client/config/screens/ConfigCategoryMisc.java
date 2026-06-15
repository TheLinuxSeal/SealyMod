package seal.thelinuxseal.sealymod.client.config.screens;

import seal.thelinuxseal.sealymod.client.config.SealyModConfig;
import seal.thelinuxseal.sealymod.client.config.screens.misc.ConfigCategoryFireBillboardFix;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;

public class ConfigCategoryMisc {
    // Changed name to 'get' and added 'static' for clean access
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.misc.title"))

                // Create a grouped sub-category
                .group(ConfigCategoryFireBillboardFix.get(parent, config))
                .build(); // Builds the Misc Category
    }
}