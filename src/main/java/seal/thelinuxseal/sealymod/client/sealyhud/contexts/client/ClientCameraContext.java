package seal.thelinuxseal.sealymod.client.sealyhud.contexts.client;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.ChunkContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.PosContext;
import seal.thelinuxseal.sealymod.client.sealyhud.docs.ContextFunc;

public final class ClientCameraContext {

    private Camera camera() {
        return Minecraft.getInstance().gameRenderer.mainCamera();
    }

    public PosContext pos = new PosContext() {
        @Override
        public Vec3 position() {
            return camera().position();
        }

        @Override
        public BlockPos blockPosition() {
            return camera().blockPosition();
        }

        @Override
        public double yaw() {
            return camera().yRot();
        }

        @Override
        public double pitch() {
            return camera().xRot();
        }
    };
    @ContextFunc(path = "client.camera.chunk()", name = "Camera Chunk", desc = "Returns the chunk that the camera is in", returns = "Chunk")
    ChunkContext chunk(){
        return new ChunkContext(Minecraft.getInstance().level.getChunk(camera().blockPosition()).getPos());
    }


}