package seal.thelinuxseal.sealymod.client.config.screens.render;

import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import net.minecraft.client.gui.screens.Screen;

public class FireBillboardTweakConfigScreen {
    // Changed name to 'get' and added 'static' for clean access
    public static OptionGroup get(Screen parent, SealyModConfig config) {
        return OptionGroup.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.title"))
                .description(OptionDescription.of(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.desc")))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.enable"))
                        .binding(
                                false,
                                () -> config.render.fireBillboardEnable,
                                val -> config.render.fireBillboardEnable = val
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.widthMult"))
                        .binding(
                                0.9F,
                                () -> config.render.fireBillboardExponentialXMult,
                                val -> config.render.fireBillboardExponentialXMult = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                .range(0.00F, 1.20F))
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.heightMult"))
                        .binding(
                                0.9F,
                                () -> config.render.fireBillboardExponentialYMult,
                                val -> config.render.fireBillboardExponentialYMult = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                .range(0.00F, 1.20F))
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.widthStart"))
                        .binding(
                                0.8F,
                                () -> config.render.fireBillboardExponentialXStart,
                                val -> config.render.fireBillboardExponentialXStart = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                .range(0.00F, 5.00F))
                        .build()
                )
                .option(Option.<Float>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.heightStart"))
                        .binding(
                                0.8F,
                                () -> config.render.fireBillboardExponentialYStart,
                                val -> config.render.fireBillboardExponentialYStart = val
                        )
                        .controller(opt -> FloatFieldControllerBuilder.create(opt)
                                .range(0.00F, 5.00F))
                        .build()
                )
                .build(); // Builds the OptionGroup
    }
}