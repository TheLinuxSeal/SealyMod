package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.common;

import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class Entity {
    net.minecraft.world.entity.Entity entity;
    public Entity(net.minecraft.world.entity.Entity entity){
        this.entity = entity;
    }
    private boolean isNull(){
        return entity == null;
    }
    @ContextFunc(path = "Entity().name()", name = "Entity Name", desc = "Returns the entity's name.", returns = "String")
    public String name(){
        return entity.getName().getString();
    }
}
