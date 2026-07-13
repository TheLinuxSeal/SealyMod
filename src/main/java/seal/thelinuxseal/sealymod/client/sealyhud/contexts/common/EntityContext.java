package seal.thelinuxseal.sealymod.client.sealyhud.contexts.common;

import net.minecraft.world.entity.Entity;

public interface EntityContext {
    Entity entity();
    private boolean isNull(){
        return entity() == null;
    }
    default String name(){
        return entity().getName().getString();
    }
}
