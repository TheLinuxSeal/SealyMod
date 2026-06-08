package com.thelinuxseal.sealymod.client.config.categories;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import com.thelinuxseal.sealymod.client.config.categories.misc.ConfigCategoryFireBillboardFix;
import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigCategoryMisc {
    // Changed name to 'get' and added 'static' for clean access
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(Component.literal("Misc"))

                // Create a grouped sub-category
                .group(ConfigCategoryFireBillboardFix.get(parent, config))
                .build(); // Builds the Misc Category
    }
}