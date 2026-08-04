package io.github.thelinuxseal.sealymod.client.config.render;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import io.github.thelinuxseal.sealymod.client.SealyModClient;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import io.github.thelinuxseal.sealymod.common.config.RootConfig;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CrosshairTweakConfigGroup {
    private static Option<Integer> crosshairWidthOpt;
    private static Option<Integer> crosshairHeightOpt;
    private static Option<Integer> attackIndicatorWidthOpt;
    private static Option<Integer> attackIndicatorHeightOpt;
    private static Option<Integer> attackIndicatorOffsetOpt;

    private static void setAvailable(boolean val){
        crosshairWidthOpt.setAvailable(val);
        crosshairHeightOpt.setAvailable(val);
        attackIndicatorWidthOpt.setAvailable(val);
        attackIndicatorHeightOpt.setAvailable(val);
        attackIndicatorOffsetOpt.setAvailable(val);
    }

    private static Option<Integer> makeIntField(Component name, Supplier<Integer> sup, Consumer<Integer> con, int def){
        return Option.<Integer>createBuilder()
                .name(name)
                .binding(
                        def,
                        sup,
                        con
                )
                .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                        .min(0))
                .build();

    }

    public static OptionGroup create(Screen parent, RootConfig config) {
        crosshairWidthOpt = makeIntField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.crosshairTweak.crosshairWidth"),
                () -> config.render.crosshairWidth,
                val -> config.render.crosshairWidth = val,
                15
        );
        crosshairHeightOpt = makeIntField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.crosshairTweak.crosshairHeight"),
                () -> config.render.crosshairHeight,
                val -> config.render.crosshairHeight = val,
                15
        );
        attackIndicatorWidthOpt = makeIntField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.crosshairTweak.attackIndicatorWidth"),
                () -> config.render.attackIndicatorWidth,
                val -> config.render.attackIndicatorWidth = val,
                16
        );
        attackIndicatorHeightOpt = makeIntField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.crosshairTweak.attackIndicatorHeight"),
                () -> config.render.attackIndicatorHeight,
                val -> config.render.attackIndicatorHeight = val,
                4
        );
        attackIndicatorOffsetOpt = makeIntField(
                SealyModClient.lang.getAsComponent("sealymod.config.render.crosshairTweak.attackIndicatorOffset"),
                () -> config.render.attackIndicatorOffset,
                val -> config.render.attackIndicatorOffset = val,
                0
        );




        setAvailable(config.render.crosshairTweakEnable);

        return OptionGroup.createBuilder()
                .name(SealyModClient.lang.getAsComponent("sealymod.config.render.crosshairTweak.title"))
                .description(OptionDescription.of(SealyModClient.lang.getAsComponent("sealymod.config.render.crosshairTweak.desc")))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModClient.lang.getAsComponent("sealymod.config.render.crosshairTweak.enable"))
                        .binding(
                                false,
                                () -> config.render.crosshairTweakEnable,
                                val -> {config.render.crosshairTweakEnable = val;
                                    setAvailable(val);}
                        )
                        .controller(TickBoxControllerBuilder::create)
                        .build()
                )
                .option(crosshairWidthOpt)
                .option(crosshairHeightOpt)
                .option(attackIndicatorWidthOpt)
                .option(attackIndicatorHeightOpt)
                .option(attackIndicatorOffsetOpt)
                .build(); // Builds the OptionGroup
    }
}
