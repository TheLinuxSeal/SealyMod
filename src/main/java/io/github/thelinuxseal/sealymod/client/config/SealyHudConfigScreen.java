package io.github.thelinuxseal.sealymod.client.config;

import io.github.thelinuxseal.sealymod.client.SealyModClient;
import io.github.thelinuxseal.sealymod.common.config.RootConfig;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.HudEditor;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class SealyHudConfigScreen {
    public static ConfigCategory create(Screen parent, RootConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModClient.lang.getAsComponent("sealymod.config.sealyhud.title"))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModClient.lang.getAsComponent("sealymod.config.sealyhud.enable"))
                        .binding(
                                false,
                                () -> config.sealyHud.enable,
                                val -> config.sealyHud.enable = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(ButtonOption.createBuilder()
                        .name(SealyModClient.lang.getAsComponent("sealymod.config.sealyhud.button"))
                        .description(OptionDescription.of(SealyModClient.lang.getAsComponent("sealymod.config.sealyhud.button.desc")))
                        .action((yaclScreen, btnOpt) -> {
                            Minecraft.getInstance().setScreenAndShow(new HudEditor(yaclScreen, config));
                        })
                        .build())
                .build();
    }
}
