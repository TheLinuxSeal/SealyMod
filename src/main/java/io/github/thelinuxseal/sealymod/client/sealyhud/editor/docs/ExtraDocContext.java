package io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs;

import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.common.*;

public final class ExtraDocContext {
    public static ExtraDocContext self = new ExtraDocContext();
    @ContextClass(name="Block()")
    public Block block = new Block(null);
    @ContextClass(name="Chunk()")
    public Chunk chunk = new Chunk(null);
    @ContextClass(name="Entity()")
    public Entity entity = new Entity(null);
    @ContextClass(name="Position()")
    public Position position = new Position(null,null);
    @ContextClass(name="Rotation()")
    public Rotation rotation = new Rotation(0,0);
}
