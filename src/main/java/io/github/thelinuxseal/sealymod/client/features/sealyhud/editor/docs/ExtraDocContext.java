package io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs;

import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects.*;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects.*;

public final class ExtraDocContext {
    public static ExtraDocContext self = new ExtraDocContext();
    @ContextClass(name = "Block()")
    public Block block;
    @ContextClass(name = "Chunk()")
    public Chunk chunk;
    @ContextClass(name = "Entity()")
    public Entity entity;
    @ContextClass(name = "Position()")
    public Position position;
    @ContextClass(name = "Rotation()")
    public Rotation rotation;
    @ContextClass(name = "Item()")
    public Item item;
    @ContextClass(name = "ItemContainer()")
    public ItemContainer itemContainer;
}
