package seal.thelinuxseal.sealymod.client.sealyhud.contexts.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.PosContext;

public final class PlayerPosContext implements PosContext {

    private LocalPlayer player() {
        return Minecraft.getInstance().player;
    }

    @Override
    public Vec3 position() {
        return player().position();
    }

    @Override
    public BlockPos blockPosition() {
        return player().blockPosition();
    }

    @Override
    public float yaw() {
        return player().getYRot();
    }

    @Override
    public float pitch() {
        return player().getXRot();
    }
}