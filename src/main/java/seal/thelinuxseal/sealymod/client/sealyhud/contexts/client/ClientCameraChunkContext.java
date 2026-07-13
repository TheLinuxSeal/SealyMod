package seal.thelinuxseal.sealymod.client.sealyhud.contexts.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.ChunkContext;

public final class ClientCameraChunkContext implements ChunkContext {
    @Override
    public ChunkPos chunk(){
        return Minecraft.getInstance().level.getChunk(Minecraft.getInstance().gameRenderer.mainCamera().blockPosition()).getPos();
    }
}
