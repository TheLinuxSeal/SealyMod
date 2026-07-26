package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.objects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class Position {
    private Chunk chunkInstance = new Chunk();

    private Vec3 pos;
    private BlockPos blockPos;

    public void set(Vec3 pos, BlockPos blockPos) {
        this.pos=pos;
        this.blockPos=blockPos;
    }



    @ContextFunc(path = "Position().exactX()", name = "Exact X", desc = "Returns the exact x coordinate.", returns = "double")
    public double exactX() { return pos.x; }
    @ContextFunc(path = "Position().exactY()", name = "Exact Y", desc = "Returns the exact y coordinate.", returns = "double")
    public double exactY() { return pos.y; }
    @ContextFunc(path = "Position().exactZ()", name = "Exact Z", desc = "Returns the exact z coordinate.", returns = "double")
    public double exactZ() { return pos.z; }

    @ContextFunc(path = "Position().x()", name = "X", desc = "Returns the x coordinate, rounded to the nearest hundredth.", returns = "double")
    public double x() { return Math.round(exactX() * 100.0) / 100.0; }
    @ContextFunc(path = "Position().y()", name = "Y", desc = "Returns the y coordinate, rounded to the nearest hundredth.", returns = "double")
    public double y() { return Math.round(exactY() * 100.0) / 100.0; }
    @ContextFunc(path = "Position().z()", name = "Z", desc = "Returns the z coordinate, rounded to the nearest hundredth.", returns = "double")
    public double z() { return Math.round(exactZ() * 100.0) / 100.0; }

    @ContextFunc(path = "Position().blockX()", name = "Block X", desc = "Returns the x coordinate of the block position.", returns = "int")
    public int blockX() { return blockPos.getX(); }
    @ContextFunc(path = "Position().blockY()", name = "Block Y", desc = "Returns the y coordinate of the block position.", returns = "int")
    public int blockY() { return blockPos.getY(); }
    @ContextFunc(path = "Position().blockZ()", name = "Block Z", desc = "Returns the z coordinate of the block position.", returns = "int")
    public int blockZ() { return blockPos.getZ(); }

    @ContextFunc(name="Chunk", desc="Returns the Chunk that the position is in.", path="Position().chunk()", returns = "Chunk")
    public Chunk chunk(){
        chunkInstance.set(Minecraft.getInstance().level.getChunk(blockPos).getPos());
        return chunkInstance;
    }

    @ContextFunc(path = "Position().biome()", name = "Biome", desc = "The biome that the position is in.", returns = "String")
    public String biome(){
        Minecraft client = Minecraft.getInstance();
        return (client.level != null && client.player != null) ? client.level.getBiome(blockPos).getRegisteredName() : "...";
    }




}