package seal.thelinuxseal.sealymod.client.config.screens.render;

import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.network.chat.Component;
import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
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

    public static void setAvailable(boolean val){
        widthMultOpt.setAvailable(val);
        heightMultOpt.setAvailable(val);
        widthStartOpt.setAvailable(val);
        heightStartOpt.setAvailable(val);
    }

    public static Option<Float> makeFloatField(Component name, Supplier<Float> sup, Consumer<Float> con, float def, float min, float max){
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

    public static OptionGroup create(Screen parent, SealyModConfig config) {
         widthMultOpt = makeFloatField(
                SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.widthMult"),
                () -> config.render.fireBillboardExponentialXMult,
                val -> config.render.fireBillboardExponentialXMult = val,
                0.9F,
                0.00F,
                1.20F
        );


        heightMultOpt = makeFloatField(
                SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.heightMult"),
                () -> config.render.fireBillboardExponentialYMult,
                val -> config.render.fireBillboardExponentialYMult = val,
                0.9F,
                0.00F,
                1.20F
        );

        widthStartOpt = makeFloatField(
                SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.widthStart"),
                () -> config.render.fireBillboardExponentialXStart,
                val -> config.render.fireBillboardExponentialXStart = val,
                0.8F,
                0.00F,
                5.00F
        );
        heightStartOpt = makeFloatField(
                SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.heightStart"),
                () -> config.render.fireBillboardExponentialYStart,
                val -> config.render.fireBillboardExponentialYStart = val,
                0.8F,
                0.00F,
                5.00F
        );
        setAvailable(config.render.fireBillboardEnable);

        return OptionGroup.createBuilder()
                .name(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.title"))
                .description(OptionDescription.of(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.desc")))
                .option(Option.<Boolean>createBuilder()
                        .name(SealyModLang.getAsComponent("sealymod.config.render.fireRenderer.enable"))
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