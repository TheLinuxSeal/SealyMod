package io.github.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.common.Block;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.common.Entity;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextFunc;

public final class PlayerLookingContext {
    @ContextFunc(path = "player.looking.block()", name = "Player Looking Block", desc = "Returns the block that the player is looking at.", returns = "Block")
    public Block block() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.hitResult instanceof BlockHitResult hit) {
            return new Block(mc.level.getBlockState(hit.getBlockPos()));
        }

        return new Block(Blocks.AIR.defaultBlockState());
    };
    @ContextFunc(path = "player.looking.entity()", name = "Player Looking Entity", desc = "Returns the entity that the player is looking at.", returns = "Entity")
    public Entity entity() {
        Minecraft mc = Minecraft.getInstance();

        return new Entity(mc.crosshairPickEntity);
    };
}
