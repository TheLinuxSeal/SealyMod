package seal.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.ChunkContext;

public final class PlayerContext {
    public final PlayerPosContext pos = new PlayerPosContext();
    ChunkContext chunk(){
        return new ChunkContext(Minecraft.getInstance().level.getChunk(Minecraft.getInstance().player.blockPosition()).getPos());
    }
    public final PlayerLookingContext looking = new PlayerLookingContext();
    public String gameMode(){
        Minecraft client = Minecraft.getInstance();
        return client.gameMode != null ? client.gameMode.getPlayerMode().getName() : "...";
    }
    public String uuid(){
        Minecraft client = Minecraft.getInstance();
        return client.player != null ? client.player.getUUID().toString() : "...";
    }

}
