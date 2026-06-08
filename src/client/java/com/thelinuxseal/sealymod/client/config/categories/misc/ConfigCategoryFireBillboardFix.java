package com.thelinuxseal.sealymod.client.config.categories.misc;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigCategoryFireBillboardFix {
    // Changed name to 'get' and added 'static' for clean access
    public static OptionGroup get(Screen parent, SealyModConfig config) {
        return OptionGroup.createBuilder()
                .name(Component.literal("Fire Billboard Renderer Fixes"))
                .description(OptionDescription.of(Component.literal("Fine-tune the scaling and tapering of fire effects on entities.")))
                .option(Option.<Float>createBuilder()
                        .name(Component.literal("Fire Billboard Exponential Width Multiplier"))
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
                        .name(Component.literal("Fire Billboard Exponential Height Multiplier"))
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
                        .name(Component.literal("Fire Billboard Exponential Width Initial Value"))
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
                        .name(Component.literal("Fire Billboard Exponential Height Initial Value"))
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