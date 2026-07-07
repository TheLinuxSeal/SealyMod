package seal.thelinuxseal.sealymod.client.sealyhud.contexts.common;

import net.minecraft.world.level.ChunkPos;

public interface ChunkContext {

    ChunkPos chunk();

    default int chunkX() { return chunk().x(); }
    default int chunkZ() { return chunk().z(); }

    default int minBlockX() { return chunk().getMinBlockX(); }
    default int minBlockZ() { return chunk().getMinBlockZ(); }

    default int maxBlockX() { return chunk().getMaxBlockX(); }
    default int maxBlockZ() { return chunk().getMaxBlockZ(); }

    default int regionX() { return chunk().getRegionX(); }
    default int regionZ() { return chunk().getRegionZ(); }

    default int regionRelativeX() { return chunk().getRegionLocalX(); }
    default int regionRelativeZ() { return chunk().getRegionLocalZ(); }
}