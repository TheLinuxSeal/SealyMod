package io.github.thelinuxseal.sealymod.client.config.data;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import io.github.thelinuxseal.sealymod.client.sealyhud.element.HudElement;

import java.util.ArrayList;
import java.util.List;

public class SealyHudConfig {
    @SerialEntry
    public boolean enable = false;
    @SerialEntry
    public List<HudElement> widgets = new ArrayList<>();

    public SealyHudConfig() {
        // Provide a default layout widget so the HUD list isn't empty on the very first launch
        this.widgets.add(new HudElement("7.5", "7.5", "7.5",  "My FPS is: ${client.fps()}", false));
        this.widgets.add(new HudElement("7.5","screenheight-15","7.5","Position: ${player.pos.x()}, ${player.pos.y()}, ${player.pos.z()}",false));
    }
    public void sanitize(){
        for (HudElement element : this.widgets) {
            if (element.getXFormula()==null) element.setXFormula("");
            if (element.getYFormula()==null) element.setYFormula("");
            if (element.getTextSizeFormula()==null) element.setTextSizeFormula("");
            if (element.getTextTemplate()==null) element.setTextTemplate("");
        }
    }
}
