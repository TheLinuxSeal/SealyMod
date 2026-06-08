package com.thelinuxseal.sealymod.client.config.categories;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import com.thelinuxseal.sealymod.client.config.categories.sealyhud.ConfigSealyHUDWidgetsScreen;
import dev.isxander.yacl3.api.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigCategorySealyHUDWidgetsWrapper {
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(Component.literal("HUD Editor"))
                .group(OptionGroup.createBuilder()
                        .name(Component.literal("Overlay Element Controls"))
                        .option(ButtonOption.createBuilder()
                                .name(Component.literal("Open SealyHUD Editor"))
                                .description(OptionDescription.of(Component.literal("Opens a dedicated, high-density screen to customize and toggle all layout items side-by-side.")))
                                .action((yaclScreen, btnOpt) -> {
                                    // Drop seamlessly out of YACL and jump into your master panel layout!
                                    Minecraft.getInstance().setScreen(new ConfigSealyHUDWidgetsScreen(yaclScreen, config));
                                })
                                .build())
                        .build())
                .build();
    }
}
