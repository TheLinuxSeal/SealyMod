package seal.thelinuxseal.sealymod.client.config.data;

import dev.isxander.yacl3.config.v2.api.SerialEntry;

public class SealyModConfig {


    @SerialEntry
    public SealyHUDConfig sealyHUD;
    @SerialEntry
    public RenderConfig render;
    @SerialEntry
    public CommandsConfig commands;

    public SealyModConfig(){
        sealyHUD = new SealyHUDConfig();
        render = new RenderConfig();
        commands = new CommandsConfig();

    }

    public void sanitize(){
        sealyHUD.sanitize();
    }


}