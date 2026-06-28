package seal.thelinuxseal.sealymod.client.sealyhud.contexts.world;
import net.minecraft.client.Minecraft;

public final class WorldContext {
    public String biome(){
        Minecraft client = Minecraft.getInstance();
        return (client.level != null && client.player != null) ? client.level.getBiome(client.player.getOnPos()).getRegisteredName() : "...";
    }

    public String dim(){
        Minecraft client = Minecraft.getInstance();
        return client.level != null ? client.level.dimension().identifier().toString() : "...";
    }

}
