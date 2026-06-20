package seal.thelinuxseal.sealymod.client.config.data;

import dev.isxander.yacl3.config.v2.api.SerialEntry;
import seal.thelinuxseal.sealymod.client.sealyhud.element.SealyHUDElement;

import java.util.ArrayList;
import java.util.List;

public class SealyHUDConfig {
    @SerialEntry
    public boolean enable = false;
    @SerialEntry
    public List<SealyHUDElement> hudWidgets = new ArrayList<>();

    public SealyHUDConfig() {
        // Provide a default layout widget so the HUD list isn't empty on the very first launch
        this.hudWidgets.add(new SealyHUDElement("7.5", "7.5", "0.75",  "My FPS is: ${client.fps()}", false));
        this.hudWidgets.add(new SealyHUDElement("7.5","screenheight-15","0.75","Position: ${player.pos.x()}, ${player.pos.y()}, ${player.pos.z()}",false));
    }
    public void sanitize(){
        for (SealyHUDElement element : this.hudWidgets) {
            if (element.getXFormula()==null) element.setXFormula("");
            if (element.getYFormula()==null) element.setYFormula("");
            if (element.getTextSizeFormula()==null) element.setTextSizeFormula("0.5");
            if (element.getTextTemplate()==null) element.setTextTemplate("");
        }
    }
}
