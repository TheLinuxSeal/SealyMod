package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.objects;

import net.minecraft.world.level.ChunkPos;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class Chunk {

    ChunkPos chunk;
    public void set(ChunkPos chunk){
        this.chunk = chunk;
    }

    @ContextFunc(
            name = "Chunk X",
            desc = "Returns the chunk X coordinate.",
            path = "Chunk().chunkX()",
            returns = "int"
    )
    public int chunkX() { return chunk.x(); }

    @ContextFunc(
            name = "Chunk Z",
            desc = "Returns the chunk Z coordinate.",
            path = "Chunk().chunkZ()",
            returns = "int"
    )
    public int chunkZ() { return chunk.z(); }

    @ContextFunc(
            name = "Minimum Block X",
            desc = "Returns the X coordinate of the westernmost block in the chunk.",
            path = "Chunk().minBlockX()",
            returns = "int"
    )
    public int minBlockX() { return chunk.getMinBlockX(); }

    @ContextFunc(
            name = "Minimum Block Z",
            desc = "Returns the Z coordinate of the northernmost block in the chunk.",
            path = "Chunk().minBlockZ()",
            returns = "int"
    )
    public int minBlockZ() { return chunk.getMinBlockZ(); }

    @ContextFunc(
            name = "Maximum Block X",
            desc = "Returns the X coordinate of the easternmost block in the chunk.",
            path = "Chunk().maxBlockX()",
            returns = "int"
    )
    public int maxBlockX() { return chunk.getMaxBlockX(); }

    @ContextFunc(
            name = "Maximum Block Z",
            desc = "Returns the Z coordinate of the southernmost block in the chunk.",
            path = "Chunk().maxBlockZ()",
            returns = "int"
    )
    public int maxBlockZ() { return chunk.getMaxBlockZ(); }

    @ContextFunc(
            name = "Region X",
            desc = "Returns the X coordinate of the region containing this chunk.",
            path = "Chunk().regionX()",
            returns = "int"
    )
    public int regionX() { return chunk.getRegionX(); }

    @ContextFunc(
            name = "Region Z",
            desc = "Returns the Z coordinate of the region containing this chunk.",
            path = "Chunk().regionZ()",
            returns = "int"
    )
    public  int regionZ() { return chunk.getRegionZ(); }

    @ContextFunc(
            name = "Region Relative X",
            desc = "Returns this chunk's X coordinate relative to its region.",
            path = "Chunk().regionRelativeX()",
            returns = "int"
    )
    public  int regionRelativeX() { return chunk.getRegionLocalX(); }

    @ContextFunc(
            name = "Region Relative Z",
            desc = "Returns this chunk's Z coordinate relative to its region.",
            path = "Chunk().regionRelativeZ()",
            returns = "int"
    )
    public int regionRelativeZ() { return chunk.getRegionLocalZ(); }
}