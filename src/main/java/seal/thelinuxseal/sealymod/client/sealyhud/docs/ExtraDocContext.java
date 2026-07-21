package seal.thelinuxseal.sealymod.client.sealyhud.docs;

import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.BlockContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.ChunkContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.EntityContext;

public class ExtraDocContext {
    public static ExtraDocContext self = new ExtraDocContext();
    public BlockContext block = new BlockContext(null);
    public ChunkContext chunk = new ChunkContext(null);
    public EntityContext entity = new EntityContext(null);
}
