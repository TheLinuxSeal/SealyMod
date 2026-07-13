package seal.thelinuxseal.sealymod.client.sealyhud.contexts;

import seal.thelinuxseal.sealymod.client.sealyhud.contexts.client.ClientContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.player.PlayerContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.system.SystemContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.util.UtilContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.world.WorldContext;
import seal.thelinuxseal.sealymod.client.sealyhud.docs.SealyHUDSubAnnotation;

public class MainContext {
    public static MainContext self = new MainContext();
    @SealyHUDSubAnnotation
    public ClientContext client = new ClientContext();
    @SealyHUDSubAnnotation
    public PlayerContext player = new PlayerContext();
    @SealyHUDSubAnnotation
    public SystemContext system = new SystemContext();
    @SealyHUDSubAnnotation
    public UtilContext util = new UtilContext();
    @SealyHUDSubAnnotation
    public WorldContext world = new WorldContext();
}
