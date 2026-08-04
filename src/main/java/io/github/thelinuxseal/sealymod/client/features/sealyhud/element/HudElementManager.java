package io.github.thelinuxseal.sealymod.client.features.sealyhud.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HudElementManager {
    private static final List<HudElement> elements = new ArrayList<>();
    private static List<HudElement> unmodifiableView = Collections.unmodifiableList(elements);

    public static void clear() {
        elements.clear();
    }

    public static void add(HudElement element) {
        elements.add(element);
    }

    public static List<HudElement> getAll() {
        return unmodifiableView; // no allocation, same view every call
    }

    public static void loadFromConfig(List<HudElement> configList) {
        clear();
        for (HudElement cfg : configList) {
            add(new HudElement(
                    cfg.getXFormula(),
                    cfg.getYFormula(),
                    cfg.getTextSizeFormula(),
                    cfg.getTextTemplate(),
                    cfg.isEnabled()
            ));
        }
    }
}