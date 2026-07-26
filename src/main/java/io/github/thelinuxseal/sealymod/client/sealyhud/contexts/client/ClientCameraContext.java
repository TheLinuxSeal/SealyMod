package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.objects.Position;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.objects.Rotation;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class ClientCameraContext {

    private Camera camera() {
        return Minecraft.getInstance().gameRenderer.mainCamera();
    }

    private final Position positionInstance = new Position();
    private final Rotation rotationInstance = new Rotation();

    @ContextFunc(path = "client.camera.position()", name = "Camera Position", desc = "Returns the camera's position", returns = "Position")
    public Position position(){
        positionInstance.set(camera().position(),camera().blockPosition());
        return positionInstance;
    }
    @ContextFunc(name="Camera Rotation", desc="Returns the rotation of the camera.", path="client.camera.rotation()", returns = "Rotation")
    public Rotation rotation(){
        rotationInstance.set(camera().xRot(),camera().yRot());
        return rotationInstance;
    }
}