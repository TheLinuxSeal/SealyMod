package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.client;


import net.minecraft.client.Minecraft;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextClass;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextFunc;

public final class ClientContext {
    @ContextClass(name="Networking")
    public final ClientNetworkingContext networking = new ClientNetworkingContext();
    @ContextClass(name="Camera")
    public final ClientCameraContext camera = new ClientCameraContext();
    @ContextFunc(path = "client.fps()", name = "Client FPS", desc = "Returns the FPS.", returns = "int")
    public int fps(){
        return Minecraft.getInstance().getFps();
    }

}
