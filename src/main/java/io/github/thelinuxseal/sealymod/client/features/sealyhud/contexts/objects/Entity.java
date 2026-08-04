package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects;

import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextFunc;

import java.util.Locale;

public final class Entity {
    net.minecraft.world.entity.Entity entity;
    public void set(net.minecraft.world.entity.Entity entity){
        this.entity = entity;
    }

    private boolean isNull(){
        return entity == null;
    }

    @ContextFunc(path = "Entity().name()", name = "Entity Name", desc = "Returns the entity's name.", returns = "String")
    public String name(){
        if (isNull()) return "";
        return entity.getName().getString();
    }
    @ContextFunc(path = "Entity().id()", name = "Entity ID", desc = "Returns the entity's id, like minecraft:villager.", returns = "String")
    public String id(){
        if (isNull()) return "";
        return entity.typeHolder().getRegisteredName().toLowerCase(Locale.ROOT);
    }
    @ContextFunc(path = "Entity().uuid()", name = "Entity UUID", desc = "Returns the entity's uuid.", returns = "String")
    public String uuid(){
        if (isNull()) return "";
        return entity.getStringUUID();
    }
    @ContextFunc(path = "Entity().worldId()", name = "Entity World ID", desc = "Returns the entity's id in the world.", returns = "int")
    public int worldId(){
        return entity.getId();
    }
}
