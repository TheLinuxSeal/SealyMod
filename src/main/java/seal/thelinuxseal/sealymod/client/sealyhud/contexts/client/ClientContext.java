package seal.thelinuxseal.sealymod.client.sealyhud.contexts.client;


import net.minecraft.client.Minecraft;

public final class ClientContext {
    public final ClientNetworkingContext networking = new ClientNetworkingContext();
    public final ClientCameraContext camera = new ClientCameraContext();
    public int fps(){
        return Minecraft.getInstance().getFps();
    }
}
