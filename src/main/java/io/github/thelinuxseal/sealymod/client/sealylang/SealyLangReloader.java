package io.github.thelinuxseal.sealymod.client.sealylang;

import io.github.thelinuxseal.sealymod.client.SealyModClient;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;

public class SealyLangReloader extends SimpleReloadListener<SealyLangReloader.PreparedData> {


    @Override
    protected PreparedData prepare(SharedState state) {
        return null;
    }

    @Override
    protected void apply(PreparedData prepared, SharedState state) {
        SealyModClient.lang.reload();

    }

    public record PreparedData() {}
}