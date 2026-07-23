package io.github.thelinuxseal.sealymod.client.resources;

import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import io.github.thelinuxseal.sealymod.client.SealyModClient;

public class ResourceReloadTask extends SimpleReloadListener<ResourceReloadTask.PreparedData> {


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