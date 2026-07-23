package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.client;


import net.minecraft.client.Minecraft;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextClass;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class ClientContext {
    @ContextClass(name="Networking")
    public final ClientNetworkingContext networking = new ClientNetworkingContext();
    @ContextClass(name="Camera")
    public final ClientCameraContext camera = new ClientCameraContext();
    @ContextFunc(path = "client.fps()", name = "Client FPS", desc = "Returns the Frames Per Second", returns = "int")
    public int fps(){
        return Minecraft.getInstance().getFps();
    }
}
