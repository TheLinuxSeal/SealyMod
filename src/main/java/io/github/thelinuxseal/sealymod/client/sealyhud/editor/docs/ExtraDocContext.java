package io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs;

import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.objects.*;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

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
