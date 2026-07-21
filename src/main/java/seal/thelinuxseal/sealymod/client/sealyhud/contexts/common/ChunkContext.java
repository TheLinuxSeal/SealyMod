package seal.thelinuxseal.sealymod.client.sealyhud.contexts.common;

import net.minecraft.world.level.ChunkPos;

public final class ChunkContext {

    ChunkPos chunk;

    public ChunkContext(ChunkPos chunk){
        this.chunk = chunk;
    }

    int chunkX() { return chunk.x(); }
    int chunkZ() { return chunk.z(); }

    int minBlockX() { return chunk.getMinBlockX(); }
    int minBlockZ() { return chunk.getMinBlockZ(); }

    int maxBlockX() { return chunk.getMaxBlockX(); }
    int maxBlockZ() { return chunk.getMaxBlockZ(); }

    int regionX() { return chunk.getRegionX(); }
    int regionZ() { return chunk.getRegionZ(); }

    int regionRelativeX() { return chunk.getRegionLocalX(); }
    int regionRelativeZ() { return chunk.getRegionLocalZ(); }
}