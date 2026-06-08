package com.thelinuxseal.sealymod.client.sealyhud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SealyHUDElementManager {
    private static final List<SealyHUDElement> elements = new ArrayList<>();

    public static void clear() {
        elements.clear();
    }

    public static void add(SealyHUDElement element) {
        elements.add(element);
    }

    public static List<SealyHUDElement> getAll() {
        return Collections.unmodifiableList(elements);
    }

    public static void loadFromConfig(List<SealyHUDElement> configList) {
        clear();
        for (SealyHUDElement cfg : configList) {
            // Load everything! We don't skip disabled ones anymore.
            SealyHUDElement element = new SealyHUDElement(
                    cfg.getXFormula(),
                    cfg.getYFormula(),
                    cfg.getTextSizeFormula(),
                    cfg.getTextTemplate(),
                    cfg.getAdvancedParseMode(),
                    cfg.isEnabled()
            );
            add(element);
        }
    }
}