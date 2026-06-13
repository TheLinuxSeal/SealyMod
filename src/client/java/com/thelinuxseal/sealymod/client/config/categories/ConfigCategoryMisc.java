package com.thelinuxseal.sealymod.client.config.categories;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import com.thelinuxseal.sealymod.client.config.categories.misc.ConfigCategoryFireBillboardFix;
import com.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
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