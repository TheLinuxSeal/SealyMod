package seal.thelinuxseal.sealymod.client.config.screens.misc;

import seal.thelinuxseal.sealymod.client.config.SealyModConfig;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import net.minecraft.client.gui.screens.Screen;

public class ConfigCategoryFireBillboardFix {
    // Changed name to 'get' and added 'static' for clean access
    public static OptionGroup get(Screen parent, SealyModConfig config) {
        return OptionGroup.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.misc.fireRenderer.title"))
                .description(OptionDescription.of(SealyModLang.getAsComponent("sealymod.config.misc.fireRenderer.desc")))
                .option(Option.<Float>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.misc.fireRenderer.widthMult"))
                        .binding(
                                0.9F,
                                () -> config.fireBillboardExponentialXMult,
                                val -> config.fireBillboardExponentialXMult = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                .range(0.00F, 1.20F))
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.misc.fireRenderer.heightMult"))
                        .binding(
                                0.9F,
                                () -> config.fireBillboardExponentialYMult,
                                val -> config.fireBillboardExponentialYMult = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                .range(0.00F, 1.20F))
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.misc.fireRenderer.widthStart"))
                        .binding(
                                0.8F,
                                () -> config.fireBillboardExponentialXStart,
                                val -> config.fireBillboardExponentialXStart = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                .range(0.00F, 5.00F))
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.misc.fireRenderer.heightStart"))
                        .binding(
                                0.8F,
                                () -> config.fireBillboardExponentialYStart,
                                val -> config.fireBillboardExponentialYStart = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                .range(0.00F, 5.00F))
                        .build()
                )
                .build(); // Builds the OptionGroup
    }
}