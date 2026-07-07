package seal.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;

public final class PlayerContext {
    public final PlayerPosContext pos = new PlayerPosContext();
    public final PlayerChunkContext chunk = new PlayerChunkContext();
    public String gameMode(){
        Minecraft client = Minecraft.getInstance();
        return client.gameMode != null ? client.gameMode.getPlayerMode().getName() : "...";
    }
    public String uuid(){
        Minecraft client = Minecraft.getInstance();
        return client.player != null ? client.player.getUUID().toString() : "...";
    }

}
