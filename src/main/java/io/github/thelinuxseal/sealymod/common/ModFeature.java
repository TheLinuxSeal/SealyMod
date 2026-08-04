package io.github.thelinuxseal.sealymod.common;

public abstract class ModFeature<T extends ModFeature<T>> {

    @SuppressWarnings("unchecked")
    public T register(ModFeatureRegistry registry) {
        registry.register(this);
        return (T) this;
    }

    protected void onInitialize() {}
}