package seal.thelinuxseal.sealymod.client.sealyhud.contexts.common;


import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Locale;

public interface BlockContext {
    BlockState block();
    default String name(){
        return block().getBlock().getName().getString();
    }
    default String id(){
        return block().typeHolder().getRegisteredName().toLowerCase(Locale.ROOT);
    }

    default String getProperty(String key){
        Property<?> property = block().getBlock().getStateDefinition().getProperty(key);
        if (property == null) {
            return "";
        }
        return block().getValue(property).toString();
    }

}
