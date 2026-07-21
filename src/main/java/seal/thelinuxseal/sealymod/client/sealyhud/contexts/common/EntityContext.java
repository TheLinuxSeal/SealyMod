package seal.thelinuxseal.sealymod.client.sealyhud.contexts.common;

import net.minecraft.world.entity.Entity;

public final class EntityContext {
    Entity entity;
    public EntityContext(Entity entity){
        this.entity = entity;
    }
    private boolean isNull(){
        return entity == null;
    }
    String name(){
        return entity.getName().getString();
    }
}
