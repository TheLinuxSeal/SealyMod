package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts;

import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.client.ClientContext;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.player.PlayerContext;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.system.SystemContext;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.util.UtilContext;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.world.WorldContext;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextClass;

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
