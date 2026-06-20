package seal.thelinuxseal.sealymod.client.config.screens.commands;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;

public class GhostCommandsConfigGroup {
    public static OptionGroup create(Screen parent, SealyModConfig config) {
        return OptionGroup.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.commands.ghost.title"))
                .description(OptionDescription.of(SealyModLang.getAsComponent("sealymod.config.commands.ghost.desc")))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.commands.ghost.setBlock.enable"))
                        .binding(
                                false,
                                () -> config.commands.enableGhostSetBlock,
                                val -> {config.commands.enableGhostSetBlock = val;}
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.commands.ghost.fill.enable"))
                        .binding(
                                false,
                                () -> config.commands.enableGhostFill,
                                val -> {config.commands.enableGhostFill = val;}
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.commands.ghost.give.enable"))
                        .binding(
                                false,
                                () -> config.commands.enableGhostGive,
                                val -> {config.commands.enableGhostGive = val;}
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.commands.ghost.summon.enable"))
                        .binding(
                                false,
                                () -> config.commands.enableGhostSummon,
                                val -> {config.commands.enableGhostSummon = val;}
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .build();

    }
}
