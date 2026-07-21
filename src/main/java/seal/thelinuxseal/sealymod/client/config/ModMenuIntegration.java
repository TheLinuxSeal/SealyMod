package seal.thelinuxseal.sealymod.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.minecraft.client.gui.screens.Screen;
import seal.thelinuxseal.sealymod.client.config.screens.RootConfigScreen;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return parent -> RootConfigScreen.create(parent, ConfigHandler.get());
    }
}