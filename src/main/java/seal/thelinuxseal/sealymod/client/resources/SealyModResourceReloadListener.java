package seal.thelinuxseal.sealymod.client.resources;

import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;

public class SealyModResourceReloadListener extends SimpleReloadListener<SealyModResourceReloadListener.PreparedData> {


    @Override
    protected PreparedData prepare(SharedState state) {
        return null;
    }

    @Override
    protected void apply(PreparedData prepared, SharedState state) {
        SealyModLangManager.reload();

    }

    public record PreparedData() {}
}