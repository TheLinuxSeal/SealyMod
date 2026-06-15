package seal.thelinuxseal.sealymod.client.resources;

import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class SealyModResourceReloadListener extends SimplePreparableReloadListener<Void> implements IdentifiableResourceReloadListener {
    private final Identifier id = Identifier.fromNamespaceAndPath("sealymod", "resource_reload_listener");

    @Override
    public Identifier getFabricId() {
        return id;
    }

    // Runs on the background thread (good for heavy calculations/parsing)
    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        return null;
    }

    // Runs on the game's main thread (safe for interacting with game states)
    @Override
    protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
        SealyModLang.reload();
        // This block runs whenever the user updates resource packs OR changes language.


        // Run your shared code here
    }
}