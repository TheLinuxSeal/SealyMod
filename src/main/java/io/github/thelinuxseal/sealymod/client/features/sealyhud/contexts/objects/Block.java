package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextFunc;
import java.util.Locale;

public final class Block {
    private BlockState block;
    public void set(BlockState block){
        this.block = block;
    }
    private boolean isNull(){
        return block == null;
    }
    @ContextFunc(path = "Block().name()", name = "Block Name", desc = "Returns the block name, like 'Dirt'.", returns = "String")
    public String name(){
        if (isNull()) return "";
        return block.getBlock().getName().getString();
    }
    @ContextFunc(path = "Block().id()", name = "Block ID", desc = "Returns the block ID, like 'minecraft:dirt'", returns = "String")
    public String id(){
        if (isNull()) return "";
        return block.typeHolder().getRegisteredName().toLowerCase(Locale.ROOT);
    }
    @ContextFunc(path = "Block().getProperty(String property)", name = "Block property", desc = "Returns a block property, like 'up' when 'property' is 'facing'.", returns = "String")
    public String getProperty(String key){
        if (isNull()) return "";
        Property<?> property = block.getBlock().getStateDefinition().getProperty(key);
        if (property == null) {
            return "";
        }
        return block.getValue(property).toString();
    }
}