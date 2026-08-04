package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.world;

import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextFunc;
import net.minecraft.client.Minecraft;

public final class WorldEntitiesContext {
    @ContextFunc(
            path = "world.entities.currentWorldEntityId()",
            name = "Current World Entity ID",
            desc = "Returns the most recently allocated runtime entity ID in the current world.",
            returns = "int"
    )
    public int currentWorldEntityId() {
        return Minecraft.getInstance().level.getNextEntityId() - 1;
    }

    @ContextFunc(
            path = "world.entities.nextWorldEntityId()",
            name = "Next World Entity ID",
            desc = "Returns the next runtime entity ID that will be assigned to a newly created entity in the world.",
            returns = "int"
    )
    public int nextWorldEntityId() {
        return Minecraft.getInstance().level.getNextEntityId();
    }
}
