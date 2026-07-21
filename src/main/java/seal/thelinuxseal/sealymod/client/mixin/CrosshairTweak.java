package seal.thelinuxseal.sealymod.client.mixin;

import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import seal.thelinuxseal.sealymod.client.config.ConfigHandler;
import seal.thelinuxseal.sealymod.client.config.data.RenderConfig;

@Mixin(Hud.class)
public class CrosshairTweak {
    @Shadow private static Identifier CROSSHAIR_SPRITE;
    @Shadow private static Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE;
    @Shadow private static Identifier CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE;
    @Shadow private static Identifier CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE;
    @Shadow
    private Minecraft minecraft;
    @Shadow
    private boolean canRenderCrosshairForSpectator(HitResult hitResult){return false;};
    /**
     * @author TheLinuxSeal
     * @reason Center crosshair
     */
    @Inject(method = "extractCrosshair", at = @At("HEAD"), cancellable = true)
    private void extractCrosshair(final GuiGraphicsExtractor graphics, final DeltaTracker deltaTracker, CallbackInfo ci) {
        RenderConfig renderConfig = ConfigHandler.get().render;
        if (renderConfig.crosshairTweakEnable) {
            Options options = this.minecraft.options;
            if (options.getCameraType().isFirstPerson()) {
                if (this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR || this.canRenderCrosshairForSpectator(this.minecraft.hitResult)) {
                    if (!this.minecraft.debugEntries.isCurrentlyEnabled(DebugScreenEntries.THREE_DIMENSIONAL_CROSSHAIR)) {
                        graphics.nextStratum();
                        graphics.pose().pushMatrix().scale(0.5F);
                        graphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_SPRITE, (graphics.guiWidth() - renderConfig.crosshairWidth), (graphics.guiHeight() - renderConfig.crosshairHeight), renderConfig.crosshairWidth * 2, renderConfig.crosshairHeight * 2);
                        if (this.minecraft.options.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR) {
                            float attackStrengthScale = this.minecraft.player.getAttackStrengthScale(0.0F);
                            boolean renderMaxAttackIndicator = false;
                            if (this.minecraft.crosshairPickEntity != null && this.minecraft.crosshairPickEntity instanceof LivingEntity && attackStrengthScale >= 1.0F) {
                                renderMaxAttackIndicator = this.minecraft.player.getCurrentItemAttackStrengthDelay() > 5.0F;
                                renderMaxAttackIndicator &= this.minecraft.crosshairPickEntity.isAlive();
                                AttackRange attackRange = (AttackRange) this.minecraft.player.getActiveItem().get(DataComponents.ATTACK_RANGE);
                                renderMaxAttackIndicator &= attackRange == null || attackRange.isInRange(this.minecraft.player, this.minecraft.hitResult.getLocation());
                            }

                            int y = graphics.guiHeight() + 18 + renderConfig.attackIndicatorOffset;//(graphics.guiHeight() / 2 - 7 + 16)*2;
                            int x = graphics.guiWidth() - renderConfig.attackIndicatorWidth;
                            if (renderMaxAttackIndicator) {
                                graphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_FULL_SPRITE, x, y, renderConfig.attackIndicatorWidth * 2, renderConfig.attackIndicatorHeight * 2);
                            } else if (attackStrengthScale < 1.0F) {
                                int progress = (int) (attackStrengthScale * 17.0F);
                                graphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_BACKGROUND_SPRITE, x, y, renderConfig.attackIndicatorWidth * 2, renderConfig.attackIndicatorHeight * 2);
                                graphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_ATTACK_INDICATOR_PROGRESS_SPRITE, renderConfig.attackIndicatorWidth * 2, renderConfig.attackIndicatorHeight * 2, 0, 0, x, y, progress * 2, renderConfig.attackIndicatorHeight * 2);
                            }
                        }
                        graphics.pose().popMatrix();
                    }

                }
            }
            ci.cancel();
        }
    }
}
