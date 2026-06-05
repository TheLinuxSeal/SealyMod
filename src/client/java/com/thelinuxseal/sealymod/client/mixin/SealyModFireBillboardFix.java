package com.thelinuxseal.sealymod.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.util.LightCoordsUtil;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FlameFeatureRenderer.class)
public class SealyModFireBillboardFix {
    @Shadow
    private static void fireVertex(
            PoseStack.Pose pose,
            VertexConsumer buffer,
            float x,
            float y,
            float z,
            float u,
            float v,
            int lightCoords
    ) {
        throw new AssertionError();
    }
    /**
     * @author TheLinuxSeal
     * @reason Adjust fire billboard dimensions
     */
    @Overwrite()
    private void renderFlame(final PoseStack.Pose pose, final MultiBufferSource bufferSource, final EntityRenderState state, final Quaternionf rotation, final AtlasManager atlasManager) {
        TextureAtlasSprite fire1 = atlasManager.get(ModelBakery.FIRE_0);
        TextureAtlasSprite fire2 = atlasManager.get(ModelBakery.FIRE_1);
        float s = state.boundingBoxWidth;
        pose.scale(s, s, s);
        float r = SealyModConfigHandler.get().fireBillboardExponentialXStart;
        float ry = SealyModConfigHandler.get().fireBillboardExponentialYStart;
        float xo = 0.0F;
        float h = state.boundingBoxHeight / s;
        float yo = 0.0F;
        pose.rotate(rotation);
        pose.translate(0.0F, 0.0F, 0.3F - (float)((int)h) * 0.02F);
        float zo = 0.0F;
        int ss = 0;
        VertexConsumer buffer = bufferSource.getBuffer(Sheets.cutoutBlockSheet());

        for(int lightCoords = LightCoordsUtil.withBlock(state.lightCoords, 15); h > 0.0F; ++ss) {
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

            fireVertex(pose, buffer, -r - 0.0F, 0.0F - yo, zo, u1, v1, lightCoords);
            fireVertex(pose, buffer, r - 0.0F, 0.0F - yo, zo, u0, v1, lightCoords);
            fireVertex(pose, buffer, r - 0.0F, - yo + 2*ry, zo, u0, v0, lightCoords);
            fireVertex(pose, buffer, -r - 0.0F, - yo + 2*ry, zo, u1, v0, lightCoords);
            h -= 0.45F;
            yo -= 0.45F;
            r *= SealyModConfigHandler.get().fireBillboardExponentialXMult;
            ry *= SealyModConfigHandler.get().fireBillboardExponentialYMult;
            zo -= 0.03F;
        }

    }
}
