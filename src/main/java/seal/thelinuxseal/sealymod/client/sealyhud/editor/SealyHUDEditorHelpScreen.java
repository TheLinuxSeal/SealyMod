package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Experimental
public class SealyHUDEditorHelpScreen {
    private final SealyHUDEditor parent;
    private final ArrayList<String> data;
    public SealyHUDEditorHelpScreen(SealyHUDEditor parent){
        this.parent = parent;
        this.data = new ArrayList<String>();
        reload();

    }
    public void reload(){
        JsonElement obj = SealyModLang.getAsJsonObj("sealymod.sealyhud.editor.help.docs");

        if (obj.isJsonArray()) {
            for (JsonElement e : obj.getAsJsonArray()){
                this.data.add(e.getAsString());
            }
        } else {
            this.data.add(SealyModLang.get("sealymod.sealyhud.editor.help.error"));
        }
    }

    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta){
        graphics.fill(20,20, parent.width-20,parent.height-20,0x000000FF);
        for (int i = 0; i < this.data.size(); i++){
            graphics.text(this.parent.getFont(),this.data.get(i),20,20*i+20,0xFFFFFFFF);
        }

    }
}
