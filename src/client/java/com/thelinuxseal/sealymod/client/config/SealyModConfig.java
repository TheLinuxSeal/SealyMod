package com.thelinuxseal.sealymod.client.config;

import com.thelinuxseal.sealymod.client.sealyhud.SealyHUDElement;
import dev.isxander.yacl3.config.v2.api.SerialEntry;

import java.util.ArrayList;
import java.util.List;

public class SealyModConfig {

    @SerialEntry
    public boolean enableSealyHUD = false;
    @SerialEntry
    public float fireBillboardExponentialXMult = 0.9F;
    @SerialEntry
    public float fireBillboardExponentialYMult = 0.9F;
    @SerialEntry
    public float fireBillboardExponentialXStart = 0.8F;
    @SerialEntry
    public float fireBillboardExponentialYStart = 0.8F;

    @SerialEntry
    public List<SealyHUDElement> hudWidgets = new ArrayList<>();

    public SealyModConfig() {
        // Provide a default layout widget so the HUD list isn't empty on the very first launch
        this.hudWidgets.add(new SealyHUDElement("", "", "12",  "",  false, false));
    }
    public void sanitize(){
        for (SealyHUDElement element : this.hudWidgets) {
            if (element.getXFormula()==null) element.setXFormula("");
            if (element.getYFormula()==null) element.setYFormula("");
            if (element.getTextSizeFormula()==null) element.setTextSizeFormula("");
            if (element.getTextTemplate()==null) element.setTextTemplate("");
        }
    }
}