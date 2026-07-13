package seal.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.BlockContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.EntityContext;

public final class PlayerLookingContext {
    public final BlockContext block = new BlockContext() {
        @Override
        public BlockState block() {
            Minecraft mc = Minecraft.getInstance();

            if (mc.hitResult instanceof BlockHitResult hit) {
                return mc.level.getBlockState(hit.getBlockPos());
            }

            return Blocks.AIR.defaultBlockState();
        }
    };
    public final EntityContext entity = new EntityContext() {
        @Override
        public @Nullable Entity entity() {
            Minecraft mc = Minecraft.getInstance();

            return mc.crosshairPickEntity;
        }
    };
}
