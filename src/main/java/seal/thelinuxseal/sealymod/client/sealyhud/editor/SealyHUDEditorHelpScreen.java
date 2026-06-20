package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public class SealyHUDEditorHelpScreen {
    private SealyHUDEditor parent;
    private JsonObject data;
    public SealyHUDEditorHelpScreen(SealyHUDEditor parent){
        this.parent = parent;
        this.data = SealyModLang.getAsJsonObj("sealymod.sealyhud.editor.docs").getAsJsonObject();


    }
    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick){
        graphics.fill(20,20, parent.width-20,parent.height-20,0x000000);

    }
}
