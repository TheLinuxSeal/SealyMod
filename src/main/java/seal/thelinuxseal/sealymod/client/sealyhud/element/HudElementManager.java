package seal.thelinuxseal.sealymod.client.sealyhud.element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HudElementManager {
    private static final List<HudElement> elements = new ArrayList<>();

    public static void clear() {
        elements.clear();
    }

    public static void add(HudElement element) {
        elements.add(element);
    }

    public static List<HudElement> getAll() {
        return Collections.unmodifiableList(elements);
    }

    public static void loadFromConfig(List<HudElement> configList) {
        clear();
        for (HudElement cfg : configList) {
            // Load everything! We don't skip disabled ones anymore.
            HudElement element = new HudElement(
                    cfg.getXFormula(),
                    cfg.getYFormula(),
                    cfg.getTextSizeFormula(),
                    cfg.getTextTemplate(),
                    cfg.isEnabled()
            );
            add(element);
        }
    }
}