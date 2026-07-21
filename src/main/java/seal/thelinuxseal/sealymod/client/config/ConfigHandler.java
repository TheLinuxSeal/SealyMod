package seal.thelinuxseal.sealymod.client.config;

import seal.thelinuxseal.sealymod.client.SealyModClient;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

import com.google.gson.GsonBuilder;
import seal.thelinuxseal.sealymod.client.config.data.RootConfig;

import java.nio.file.Path;

public class ConfigHandler {

    public static final ConfigClassHandler<RootConfig> HANDLER =
            ConfigClassHandler.createBuilder(RootConfig.class)
                    .id(Identifier.fromNamespaceAndPath("sealymod", "config"))
                    .serializer(config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(getConfigPath())
                            .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                            .setJson5(true)
                            .build())
                    .build();

    private static Path getConfigPath() {
        return FabricLoader.getInstance()
                .getConfigDir()
                .resolve("sealymod.json5");
    }

    public static RootConfig get() {
        return HANDLER.instance();
    }

    public static void save() {
        HANDLER.save();
    }

    public static void load() {
        HANDLER.load(); HANDLER.instance().sanitize();
    }
}