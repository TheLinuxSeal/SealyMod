package com.thelinuxseal.sealymod.client.config.categories;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigCategoryGeneral {
    // Changed name to 'get' and added 'static' for clean access
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(Component.literal("General"))
                .option(Option.<Boolean>createBuilder()
                        .name(Component.literal("Enable HUD"))
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