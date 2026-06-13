package com.thelinuxseal.sealymod.client.config.categories;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import com.thelinuxseal.sealymod.client.config.categories.sealyhud.ConfigSealyHUDWidgetsScreen;
import com.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ConfigCategorySealyHUDWidgetsWrapper {
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.sealyhud.title"))
                .option(ButtonOption.createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.sealyhud.button"))
                        .description(OptionDescription.of(SealyModLang.getAsComponent("sealymod.config.sealyhud.button.desc")))
                        .action((yaclScreen, btnOpt) -> {
                            // Drop seamlessly out of YACL and jump into your master panel layout!
                            Minecraft.getInstance().setScreen(new ConfigSealyHUDWidgetsScreen(yaclScreen, config));
                        })
                        .build())
                .build();
    }
}
