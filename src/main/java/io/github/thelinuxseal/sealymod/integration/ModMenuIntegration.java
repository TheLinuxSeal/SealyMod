package io.github.thelinuxseal.sealymod.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import io.github.thelinuxseal.sealymod.client.config.RootConfigScreen;
import io.github.thelinuxseal.sealymod.common.ConfigHandler;
import net.minecraft.client.gui.screens.Screen;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return parent -> RootConfigScreen.create(parent, ConfigHandler.get());
    }
}