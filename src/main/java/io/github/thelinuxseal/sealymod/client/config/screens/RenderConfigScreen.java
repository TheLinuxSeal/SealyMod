package io.github.thelinuxseal.sealymod.client.config.screens;

import io.github.thelinuxseal.sealymod.client.SealyModClient;
import io.github.thelinuxseal.sealymod.client.config.data.RootConfig;
import io.github.thelinuxseal.sealymod.client.config.screens.render.CrosshairTweakConfigGroup;
import io.github.thelinuxseal.sealymod.client.config.screens.render.FireBillboardTweakConfigGroup;
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