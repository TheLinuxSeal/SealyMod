package seal.thelinuxseal.sealymod.client.config.screens;

import seal.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.*;
import net.minecraft.client.gui.screens.Screen;

public class MainConfigScreen {

    public static Screen create(Screen parent, SealyModConfig config) {
        return YetAnotherConfigLib.createBuilder()
                .title(SealyModLang.getAsComponent("sealymod.config.title"))
                .save(SealyModConfigHandler::save)
                .category(SealyHUDConfigScreen.create(parent,config))
                .category(RenderConfigScreen.create(parent, config))
                .build()
                .generateScreen(parent);
    }
}