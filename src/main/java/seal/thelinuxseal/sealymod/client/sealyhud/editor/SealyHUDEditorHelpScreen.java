package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;

@ApiStatus.Experimental
public class SealyHUDEditorHelpScreen {
    private final SealyHUDEditor parent;
    private JsonObject data;
    public SealyHUDEditorHelpScreen(SealyHUDEditor parent){
        this.parent = parent;
        this.data = new ArrayList<String>();
        reload();

    }
    public void reload(){
        JsonObject obj = SealyModLangManager.SEALYHUD_DOCS.getAll();

        if (obj.isJsonNull()) {
            for (JsonElement e : obj.getAsJsonArray()) {
                this.data.add(e.getAsString());
            }
        } else {
            this.data.add(SealyModLangManager.MAIN.get("sealymod.sealyhud.editor.help.error"));
        }
    }

    void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta){
        graphics.fill(20,20, parent.width-20,parent.height-20,0x000000FF);
        for (int i = 0; i < this.data.size(); i++){
            //graphics.text(this.parent.getFont(),this.data.get(i),20, (int) (20*i+20-parent.scrollAmount),0xFFFFFFFF);
        }

    }
}
