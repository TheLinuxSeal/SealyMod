package seal.thelinuxseal.sealymod.client.sealyhud.contexts.common;


import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import seal.thelinuxseal.sealymod.client.sealyhud.docs.ContextFunc;

import java.util.Locale;

public final class BlockContext {
    BlockState block;

    public BlockContext(BlockState block){
        this.block = block;
    }
    @ContextFunc(path = "Block().name()", name = "Block Name", desc = "Returns the block name, like 'Dirt'.", returns = "String")
    String name(){
        return block.getBlock().getName().getString();
    }
    @ContextFunc(path = "Block().id()", name = "Block ID", desc = "Returns the block ID, like 'minecraft:dirt'", returns = "String")
    String id(){
        return block.typeHolder().getRegisteredName().toLowerCase(Locale.ROOT);
    }
    @ContextFunc(path = "Block().getProperty(String property)", name = "Block property", desc = "Returns a block property, like 'up' when 'property' is 'facing'.", returns = "String")
    String getProperty(String key){
        Property<?> property = block.getBlock().getStateDefinition().getProperty(key);
        if (property == null) {
            return "";
        }
        return block.getValue(property).toString();
    }

}
