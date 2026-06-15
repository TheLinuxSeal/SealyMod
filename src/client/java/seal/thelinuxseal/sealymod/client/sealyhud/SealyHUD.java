package seal.thelinuxseal.sealymod.client.sealyhud;

import seal.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import seal.thelinuxseal.sealymod.client.sealyhud.element.SealyHUDElement;
import seal.thelinuxseal.sealymod.client.sealyhud.element.SealyHUDElementManager;
import seal.thelinuxseal.sealymod.client.sealyhud.parser.SealyHUDTextParser;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;

import seal.thelinuxseal.sealymod.SealyMod;

public class SealyHUD {

    public void init() {
        SealyHUDTextParser.init();
        SealyHUDElementManager.loadFromConfig(SealyModConfigHandler.get().hudWidgets);
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(SealyMod.MOD_ID, "last_element"), this.hudLayer());
    }

    private HudElement hudLayer() {
        return (graphics, deltaTracker) -> {
            if (!SealyModConfigHandler.get().enableSealyHUD) {
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
                ///element.getTextSize()
                graphics.pose().pushMatrix();
                graphics.pose().translate(element.getX(), element.getY());
                graphics.pose().scale(
                        element.getTextSize(),
                        element.getTextSize()
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