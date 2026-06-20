package seal.thelinuxseal.sealymod.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.minecraft.client.gui.screens.Screen;
import seal.thelinuxseal.sealymod.client.config.screens.MainConfigScreen;

public class SealyModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return parent -> MainConfigScreen.create(parent, SealyModConfigHandler.get());
    }
}