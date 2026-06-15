package seal.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;

public final class PlayerContext {
    public final PlayerPosContext pos = new PlayerPosContext();
    public String gameMode(){
        Minecraft client = Minecraft.getInstance();
        return client.gameMode != null ? client.gameMode.getPlayerMode().getName() : "...";
    }
    public String uuid(){
        return Minecraft.getInstance().player.getUUID().toString();
    }

}
