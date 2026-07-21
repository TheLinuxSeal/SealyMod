package seal.thelinuxseal.sealymod.client.sealyhud.contexts;

import seal.thelinuxseal.sealymod.client.sealyhud.contexts.client.ClientContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.player.PlayerContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.system.SystemContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.util.UtilContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.world.WorldContext;

public final class MainContext {
    public static MainContext self = new MainContext();
    public ClientContext client = new ClientContext();
    public PlayerContext player = new PlayerContext();
    public SystemContext system = new SystemContext();
    public UtilContext util = new UtilContext();
    public WorldContext world = new WorldContext();
}
