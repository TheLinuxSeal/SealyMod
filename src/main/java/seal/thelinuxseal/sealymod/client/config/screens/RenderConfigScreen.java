package seal.thelinuxseal.sealymod.client.config.screens;

import seal.thelinuxseal.sealymod.client.SealyModClient;
import seal.thelinuxseal.sealymod.client.config.data.RootConfig;
import seal.thelinuxseal.sealymod.client.config.screens.render.CrosshairTweakConfigGroup;
import seal.thelinuxseal.sealymod.client.config.screens.render.FireBillboardTweakConfigGroup;
import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;

public class RenderConfigScreen {
    public static ConfigCategory create(Screen parent, RootConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModClient.lang.getAsComponent("sealymod.config.render.title"))

                // Create a grouped sub-category
                .group(FireBillboardTweakConfigGroup.create(parent, config))
                .group(CrosshairTweakConfigGroup.create(parent, config))
                .build(); // Builds the Misc Category
    }
}