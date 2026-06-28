package seal.thelinuxseal.sealymod.client.config.screens;

import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.config.screens.render.FireBillboardTweakConfigGroup;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;
import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;

public class RenderConfigScreen {
    public static ConfigCategory create(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModLangManager.MAIN.getAsComponent("sealymod.config.render.title"))

                // Create a grouped sub-category
                .group(FireBillboardTweakConfigGroup.create(parent, config))
                .build(); // Builds the Misc Category
    }
}