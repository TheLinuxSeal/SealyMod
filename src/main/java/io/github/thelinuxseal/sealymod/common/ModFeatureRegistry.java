package io.github.thelinuxseal.sealymod.common;

import java.util.ArrayList;
import java.util.List;

import static io.github.thelinuxseal.sealymod.common.SealyMod.LOGGER;

public class ModFeatureRegistry {
    private final List<ModFeature> FEATURES = new ArrayList<>();
    private boolean locked = false;

    public <T extends ModFeature> T register(T feature) {
        if (locked) {
            LOGGER.error("Cannot register mod features after initialization");
            return feature;
        }

        FEATURES.add(feature);
        return feature;
    }

    public void initialize() {
        if (locked){
            LOGGER.warn("Cannot initialize mod features after initialization");
            return;
        }
        locked = true;
        for (ModFeature feature : FEATURES) {
            try {
                feature.onInitialize();
            } catch (Exception e) {
                LOGGER.error("Failed to initialize feature {}", feature.getClass().getName(), e);
            }
        }
    }
}