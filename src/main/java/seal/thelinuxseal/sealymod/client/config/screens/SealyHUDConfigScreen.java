package seal.thelinuxseal.sealymod.client.config.screens;

import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;
import seal.thelinuxseal.sealymod.client.sealyhud.editor.SealyHUDEditor;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class SealyHUDConfigScreen {
    public static ConfigCategory create(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModLangManager.MAIN.getAsComponent("sealymod.config.sealyhud.title"))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLangManager.MAIN.getAsComponent("sealymod.config.sealyhud.enable"))
                        .binding(
                                false,
                                () -> config.sealyHUD.enable,
                                val -> config.sealyHUD.enable = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(ButtonOption.createBuilder()
                        .name(SealyModLangManager.MAIN.getAsComponent("sealymod.config.sealyhud.button"))
                        .description(OptionDescription.of(SealyModLangManager.MAIN.getAsComponent("sealymod.config.sealyhud.button.desc")))
                        .action((yaclScreen, btnOpt) -> {
                            Minecraft.getInstance().setScreenAndShow(new SealyHUDEditor(yaclScreen, config));
                        })
                        .build())
                .build();
    }
}
