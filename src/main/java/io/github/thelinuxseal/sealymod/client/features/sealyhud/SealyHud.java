package io.github.thelinuxseal.sealymod.client.features.sealyhud;

import io.github.thelinuxseal.sealymod.common.ModFeature;
import io.github.thelinuxseal.sealymod.client.SealyModClient;
import io.github.thelinuxseal.sealymod.common.ConfigHandler;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.element.HudElement;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.element.HudElementManager;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.parser.HudTextParser;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;

public class SealyHud extends ModFeature  {

    @Override
    public void onInitialize() {
        HudTextParser.init();
        HudElementManager.loadFromConfig(ConfigHandler.get().sealyHud.widgets);
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(SealyModClient.MOD_ID, "last_element"), this.hudLayer());
    }

    private net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement hudLayer() {
        return (graphics, deltaTracker) -> {
            if (!ConfigHandler.get().sealyHud.enable) {
                return;
            }
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.player == null || minecraft.level == null) {
                return;
            }

            for (HudElement element : HudElementManager.getAll()) {
                // Check the toggle right here before rendering!
                if (!element.isEnabled()) {
                    continue;
                }
                ///element.getTextSize()
                graphics.pose().pushMatrix();
                graphics.pose().translate(element.getX(), element.getY());
                graphics.pose().scale(
                        element.getTextSize()/10,
                        element.getTextSize()/10
                );

                graphics.text(
                        minecraft.font,
                        element.getText(),
                        0,
                        0,
                        0xFFFFFFFF,
                        false
                );

                graphics.pose().popMatrix();
            }
        };
    }

}