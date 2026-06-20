package seal.thelinuxseal.sealymod.client.config.data;

import dev.isxander.yacl3.config.v2.api.SerialEntry;

public class CommandsConfig {
    @SerialEntry
    public boolean enableGhostSetBlock = false;
    @SerialEntry
    public boolean enableGhostFill = false;
    @SerialEntry
    public boolean enableGhostGive = false;
    @SerialEntry
    public boolean enableGhostSummon = false;
}
