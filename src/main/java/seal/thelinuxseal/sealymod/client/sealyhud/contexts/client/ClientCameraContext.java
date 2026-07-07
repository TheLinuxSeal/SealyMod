package seal.thelinuxseal.sealymod.client.sealyhud.contexts.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.PosContext;

public final class ClientCameraContext implements PosContext {

    public final ClientCameraChunkContext chunk = new ClientCameraChunkContext();

    private Camera camera() {
        return Minecraft.getInstance().gameRenderer.mainCamera();
    }

    @Override
    public Vec3 position() {
        return camera().position();
    }

    @Override
    public BlockPos blockPosition() {
        return camera().blockPosition();
    }

    @Override
    public float yaw() {
        return camera().yRot();
    }

    @Override
    public float pitch() {
        return camera().xRot();
    }


}