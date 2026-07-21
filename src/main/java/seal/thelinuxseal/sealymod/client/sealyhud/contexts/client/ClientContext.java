package seal.thelinuxseal.sealymod.client.sealyhud.contexts.client;


import net.minecraft.client.Minecraft;
import seal.thelinuxseal.sealymod.client.sealyhud.docs.ContextFunc;

public final class ClientContext {
    public final ClientNetworkingContext networking = new ClientNetworkingContext();
    public final ClientCameraContext camera = new ClientCameraContext();
    @ContextFunc(path = "client.fps()", name = "Client FPS", desc = "Returns the Frames Per Second", returns = "int")
    public int fps(){
        return Minecraft.getInstance().getFps();
    }
}
