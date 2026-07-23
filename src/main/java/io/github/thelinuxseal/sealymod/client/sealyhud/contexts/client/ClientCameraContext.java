package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.common.Position;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.common.Rotation;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class ClientCameraContext {

    private Camera camera() {
        return Minecraft.getInstance().gameRenderer.mainCamera();
    }

    @ContextFunc(path = "client.camera.position()", name = "Camera Position", desc = "Returns the camera's position", returns = "Position")
    public Position position(){return new Position(camera().position(),camera().blockPosition());}
    @ContextFunc(name="Player Rotation", desc="Returns the rotation of the player.", path="player.rotation()", returns = "Rotation")
    public Rotation rotation(){return new Rotation(camera().xRot(), camera().yRot());}


}