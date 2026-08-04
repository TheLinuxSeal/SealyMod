package io.github.thelinuxseal.sealymod.common.config;

import dev.isxander.yacl3.config.v2.api.SerialEntry;

public class RootConfig {
    @SerialEntry
    public int cfgVer = 1;

    @SerialEntry
    public SealyHudConfig sealyHud;
    @SerialEntry
    public RenderConfig render;

    public RootConfig(){
        sealyHud = new SealyHudConfig();
        render = new RenderConfig();

    }

    public void sanitize(){
        sealyHud.sanitize();
    }


}