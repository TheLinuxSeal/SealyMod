package com.thelinuxseal.sealymod.client.config.categories;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import com.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;

public class ConfigCategoryGeneral {
    // Changed name to 'get' and added 'static' for clean access
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.general.title"))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.general.enableSealyhud"))
                        .binding(
                                false,
                                () -> config.enableSealyHUD,
                                val -> config.enableSealyHUD = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .build();
    }
}