package seal.thelinuxseal.sealymod.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import seal.thelinuxseal.sealymod.client.config.ConfigHandler;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.LightCoordsUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import seal.thelinuxseal.sealymod.client.config.data.RenderConfig;

@Mixin(FlameFeatureRenderer.class)
public class FireBillboardTweak {

    @Shadow
    private static void fireVertex(
            final PoseStack.Pose pose,
            final VertexConsumer buffer,
            final float x,
            final float y,
            final float z,
            final float u,
            final float v,
            final int lightCoords
    ) {
        throw new AssertionError();
    }

    /**
     * @author TheLinuxSeal
     * @reason Adjust fire billboard dimensions for new rendering engine structure
     */
    @Inject(method = "prepare", at = @At("HEAD"), cancellable = true)
    private void prepare(
            FlameFeatureRenderer.Submit submit, VertexConsumer buffer, TextureAtlasSprite fire1, TextureAtlasSprite fire2, CallbackInfo ci
    ) {
        RenderConfig renderConfig = ConfigHandler.get().render;
        if (renderConfig.fireBillboardEnable) {
            PoseStack.Pose pose = submit.pose();
            EntityRenderState state = submit.entityRenderState();

            // CUSTOM: Adjusted scale multiplier removed
            float s = state.boundingBoxWidth;
            pose.scale(s, s, s);

            // CUSTOM: Custom configuration values initialized
            float r = ConfigHandler.get().render.fireBillboardExponentialXStart;
            float ry = ConfigHandler.get().render.fireBillboardExponentialYStart;

            float xo = 0.0F;
            float h = state.boundingBoxHeight / s;
            float yo = 0.0F;
            pose.rotate(submit.rotation());
            pose.translate(0.0F, 0.0F, 0.3F - (int) h * 0.02F);
            float zo = 0.0F;
            int ss = 0;
            int lightCoords = LightCoordsUtil.withBlock(state.lightCoords, 15);

            while (h > 0.0F) {
                TextureAtlasSprite tex = ss % 2 == 0 ? fire1 : fire2;
                float u0 = tex.getU0();
                float v0 = tex.getV0();
                float u1 = tex.getU1();
                float v1 = tex.getV1();
                if (ss / 2 % 2 == 0) {
                    float tmp = u1;
                    u1 = u0;
                    u0 = tmp;
                }

                // CUSTOM: Vertex coordinates modified using custom layout logic
                fireVertex(pose, buffer, -r - 0.0F, 0.0F - yo, zo, u1, v1, lightCoords);
                fireVertex(pose, buffer, r - 0.0F, 0.0F - yo, zo, u0, v1, lightCoords);
                fireVertex(pose, buffer, r - 0.0F, -yo + 2 * ry, zo, u0, v0, lightCoords);
                fireVertex(pose, buffer, -r - 0.0F, -yo + 2 * ry, zo, u1, v0, lightCoords);

                h -= 0.45F;
                yo -= 0.45F;

                // CUSTOM: Custom multipliers applied here
                r *= ConfigHandler.get().render.fireBillboardExponentialXMult;
                ry *= ConfigHandler.get().render.fireBillboardExponentialYMult;

                zo -= 0.03F;
                ss++;
            }
            ci.cancel();
        }
    }
}