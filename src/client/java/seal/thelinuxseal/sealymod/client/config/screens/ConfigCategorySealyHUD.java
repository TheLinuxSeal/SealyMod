package seal.thelinuxseal.sealymod.client.config.screens;

import seal.thelinuxseal.sealymod.client.config.SealyModConfig;
import seal.thelinuxseal.sealymod.client.sealyhud.SealyHUDEditor;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ConfigCategorySealyHUD {
    public static ConfigCategory get(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.sealyhud.title"))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.sealyhud.enable"))
                        .binding(
                                false,
                                () -> config.enableSealyHUD,
                                val -> config.enableSealyHUD = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(ButtonOption.createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.sealyhud.button"))
                        .description(OptionDescription.of(SealyModLang.getAsComponent("sealymod.config.sealyhud.button.desc")))
                        .action((yaclScreen, btnOpt) -> {
                            // Drop seamlessly out of YACL and jump into your master panel layout!
                            Minecraft.getInstance().setScreen(new SealyHUDEditor(yaclScreen, config));
                        })
                        .build())
                .build();
    }
}
