package seal.thelinuxseal.sealymod.client.config.data;

import dev.isxander.yacl3.config.v2.api.SerialEntry;

public class RootConfig {
    @SerialEntry
    public String cfgVer = "0.1.0";

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