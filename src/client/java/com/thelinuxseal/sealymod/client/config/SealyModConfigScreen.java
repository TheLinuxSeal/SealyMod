package com.thelinuxseal.sealymod.client.config;

import com.thelinuxseal.sealymod.client.config.categories.*;
import com.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.*;
import net.minecraft.client.gui.screens.Screen;



public class SealyModConfigScreen {

    public static Screen create(Screen parent, SealyModConfig config) {
        return YetAnotherConfigLib.createBuilder()
                .title(SealyModLang.getAsComponent("sealymod.config.title"))
                .save(SealyModConfigHandler::save)
                .category(ConfigCategoryGeneral.get(parent, config))
                .category(ConfigCategoryMisc.get(parent, config))
                .category(ConfigCategorySealyHUDWidgetsWrapper.get(parent,config))
                .build()
                .generateScreen(parent);
    }
}