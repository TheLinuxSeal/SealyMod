package seal.thelinuxseal.sealymod.client.config.screens;

import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.client.gui.screens.Screen;
import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.config.screens.commands.GhostCommandsConfigGroup;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;

public class CommandsConfigScreen {
    public static ConfigCategory create(Screen parent, SealyModConfig config) {
        return ConfigCategory.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.commands.title"))
                .group(GhostCommandsConfigGroup.create(parent, config))
                .build();
    }
}
