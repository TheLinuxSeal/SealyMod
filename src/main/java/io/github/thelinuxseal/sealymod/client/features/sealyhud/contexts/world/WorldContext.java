package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.world;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextClass;

public final class WorldContext {
    @ContextClass(name="Entities")
    public final WorldEntitiesContext entities = new WorldEntitiesContext();

}
