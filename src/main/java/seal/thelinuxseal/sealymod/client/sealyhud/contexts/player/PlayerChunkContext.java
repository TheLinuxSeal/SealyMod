package seal.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.ChunkContext;

public final class PlayerChunkContext implements ChunkContext {
    @Override
    public ChunkPos chunk() {
        return Minecraft.getInstance().player.chunkPosition();
    }
}
