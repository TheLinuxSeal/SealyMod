package io.github.thelinuxseal.sealymod.client.sealylang;

import io.github.thelinuxseal.sealymod.client.SealyModClient;
import io.github.thelinuxseal.sealymod.common.ModFeature;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;

public class SealyLang {
    public void init() {
        ResourceLoader.get(PackType.CLIENT_RESOURCES).registerReloadListener(
                Identifier.fromNamespaceAndPath(SealyModClient.MOD_ID, "sealylang"), // Unique listener ID
                new SealyLangReloader()
        );

    }
}