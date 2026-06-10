package com.thelinuxseal.sealymod.client.resources;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class SealyModResourceReloadListenerInitializer {
    public static void init(){
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
                .registerReloadListener(new SealyModResourceReloadListener());
    }
}
