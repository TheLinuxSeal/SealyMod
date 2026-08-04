package io.github.thelinuxseal.sealymod.client.config.render;

import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import io.github.thelinuxseal.sealymod.client.SealyModClient;
import net.minecraft.network.chat.Component;
import io.github.thelinuxseal.sealymod.common.config.RootConfig;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.FloatFieldControllerBuilder;
import net.minecraft.client.gui.screens.Screen;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FireBillboardTweakConfigGroup {
    private static Option<Float> widthMultOpt;
    private static Option<Float> heightMultOpt;
    private static Option<Float> widthStartOpt;
    private static Option<Float> heightStartOpt;

    private static void setAvailable(boolean val){
        widthMultOpt.setAvailable(val);
        heightMultOpt.setAvailable(val);
        widthStartOpt.setAvailable(val);
        heightStartOpt.setAvailable(val);
    }

    private static Option<Float> makeFloatField(Component name, Supplier<Float> sup, Consumer<Float> con, float def, float min, float max){
        return Option.<Float>createBuilder()
                .name(name)
                .binding(
                        def,
                        sup,
                        con
                )
                .controller(opt -> FloatFieldControllerBuilder.create(opt)
                        .range(min, max))
                .build();

    }

    public static OptionGroup create(Screen parent, RootConfig config) {
         widthMultOpt = makeFloatField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.fireRenderer.widthMult"),
                () -> config.render.fireBillboardExponentialXMult,
                val -> config.render.fireBillboardExponentialXMult = val,
                0.9F,
                0.00F,
                1.20F
        );


        heightMultOpt = makeFloatField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.fireRenderer.heightMult"),
                () -> config.render.fireBillboardExponentialYMult,
                val -> config.render.fireBillboardExponentialYMult = val,
                0.9F,
                0.00F,
                1.20F
        );

        widthStartOpt = makeFloatField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.fireRenderer.widthStart"),
                () -> config.render.fireBillboardExponentialXStart,
                val -> config.render.fireBillboardExponentialXStart = val,
                0.8F,
                0.00F,
                5.00F
        );
        heightStartOpt = makeFloatField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.fireRenderer.heightStart"),
                () -> config.render.fireBillboardExponentialYStart,
                val -> config.render.fireBillboardExponentialYStart = val,
                0.8F,
                0.00F,
                5.00F
        );
        setAvailable(config.render.fireBillboardEnable);

        return OptionGroup.createBuilder()
                .name(SealyModClient.lang.getAsComponent("sealymod.config.render.fireRenderer.title"))
                .description(OptionDescription.of(SealyModClient.lang.getAsComponent("sealymod.config.render.fireRenderer.desc")))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModClient.lang.getAsComponent("sealymod.config.render.fireRenderer.enable"))
                        .binding(
                                false,
                                () -> config.render.fireBillboardEnable,
                                val -> {config.render.fireBillboardEnable = val;
                                                setAvailable(val);}
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(widthMultOpt)
                .option(heightMultOpt)
                .option(widthStartOpt)
                .option(heightStartOpt)
                .build(); // Builds the OptionGroup
    }
}