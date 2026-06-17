package seal.thelinuxseal.sealymod.client.resources;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

public class SealyModResourceReloadListenerInitializer {
    public static void init() {
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(
                Identifier.fromNamespaceAndPath("sealymod", "sealy_resource_listener"), // Unique listener ID
                new SealyModResourceReloadListener()
        );

    }
}