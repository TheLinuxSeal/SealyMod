package com.thelinuxseal.sealymod.client.config;

import com.thelinuxseal.sealymod.client.config.categories.*;
import dev.isxander.yacl3.api.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;


public class SealyModConfigScreen {

    public static Screen create(Screen parent, SealyModConfig config) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("SealyMod"))
                .save(SealyModConfigHandler::save)
                .category(ConfigCategoryGeneral.get(parent, config))
                .category(ConfigCategoryMisc.get(parent, config))
                .category(ConfigCategorySealyHUDWidgetsWrapper.get(parent,config))
                .build()
                .generateScreen(parent);
    }
}