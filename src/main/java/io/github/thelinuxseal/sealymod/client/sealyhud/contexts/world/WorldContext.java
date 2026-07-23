package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.world;
import net.minecraft.client.Minecraft;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class WorldContext {
    @ContextFunc(path = "world.biome()", name = "Biome", desc = "The biome that the player is in.", returns = "String")
    public String biome(){
        Minecraft client = Minecraft.getInstance();
        return (client.level != null && client.player != null) ? client.level.getBiome(client.player.getOnPos()).getRegisteredName() : "...";
    }
    @ContextFunc(path = "world.dim()", name = "Dimension", desc = "The dimension that the player is in.", returns = "String")
    public String dim(){
        Minecraft client = Minecraft.getInstance();
        return client.level != null ? client.level.dimension().identifier().toString() : "...";
    }

}
