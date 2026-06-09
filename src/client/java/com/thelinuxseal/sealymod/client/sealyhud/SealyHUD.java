package com.thelinuxseal.sealymod.client.sealyhud;

import com.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;

import com.thelinuxseal.sealymod.SealyMod;

public class SealyHUD {

    public void init() {
        SealyHUDElementManager.loadFromConfig(SealyModConfigHandler.get().hudWidgets);
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(SealyMod.MOD_ID, "last_element"), this.hudLayer());
    }

    private HudElement hudLayer() {
        return (graphics, deltaTracker) -> {
            if (!SealyModConfigHandler.get().enableSealyHUD){
                return;
            }
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.player == null || minecraft.level == null) {
                return;
            }

            for (SealyHUDElement element : SealyHUDElementManager.getAll()) {
                // Check the toggle right here before rendering!
                if (!element.isEnabled()) {
                    continue;
                }


                graphics.text(
                        minecraft.font,
                        element.getText(),
                        element.getX(),
                        element.getY(),
                        0xFFFFFFFF,
                        false
                );
            }
        };
    }

}