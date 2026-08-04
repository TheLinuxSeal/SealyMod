package io.github.thelinuxseal.sealymod.client.config;

import io.github.thelinuxseal.sealymod.client.SealyModClient;
import io.github.thelinuxseal.sealymod.common.ConfigHandler;
import io.github.thelinuxseal.sealymod.common.config.RootConfig;
import dev.isxander.yacl3.api.*;
import net.minecraft.client.gui.screens.Screen;

public class RootConfigScreen {

    public static Screen create(Screen parent, RootConfig config) {
        return YetAnotherConfigLib.createBuilder()
                .title(SealyModClient.lang.getAsComponent("sealymod.config.title"))
                .save(ConfigHandler::save)
                .category(SealyHudConfigScreen.create(parent,config))
                .category(RenderConfigScreen.create(parent, config))
                .build()
                .generateScreen(parent);
    }
}