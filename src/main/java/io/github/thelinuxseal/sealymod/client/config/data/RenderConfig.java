package io.github.thelinuxseal.sealymod.client.config.data;

import dev.isxander.yacl3.config.v2.api.SerialEntry;

public class RenderConfig {
    @SerialEntry
    public boolean fireBillboardEnable = false;
    @SerialEntry
    public float fireBillboardExponentialXMult = 0.9F;
    @SerialEntry
    public float fireBillboardExponentialYMult = 0.9F;
    @SerialEntry
    public float fireBillboardExponentialXStart = 0.8F;
    @SerialEntry
    public float fireBillboardExponentialYStart = 0.8F;
    @SerialEntry
    public boolean crosshairTweakEnable = false;
    @SerialEntry
    public int crosshairWidth = 15;
    @SerialEntry
    public int crosshairHeight = 15;
    @SerialEntry
    public int attackIndicatorWidth = 16;
    @SerialEntry
    public int attackIndicatorHeight = 4;
    @SerialEntry
    public int attackIndicatorOffset = 0;
}
