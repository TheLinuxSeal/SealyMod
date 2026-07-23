package io.github.thelinuxseal.sealymod.client.sealyhud.contexts;

import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.client.ClientContext;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.player.PlayerContext;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.system.SystemContext;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.util.UtilContext;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.world.WorldContext;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs.ContextClass;

public final class MainContext {
    public static MainContext self = new MainContext();
    @ContextClass(name="Client")
    public ClientContext client = new ClientContext();
    @ContextClass(name="Player")
    public PlayerContext player = new PlayerContext();
    @ContextClass(name="System")
    public SystemContext system = new SystemContext();
    @ContextClass(name="Util")
    public UtilContext util = new UtilContext();
    @ContextClass(name="World")
    public WorldContext world = new WorldContext();
}
