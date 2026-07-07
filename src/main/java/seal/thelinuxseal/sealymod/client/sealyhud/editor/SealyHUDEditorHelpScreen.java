package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@ApiStatus.Experimental
public class SealyHUDEditorHelpScreen {
    private final SealyHUDEditor parent;
    private JsonObject data;
    private List<String> keys;
    public SealyHUDEditorHelpScreen(SealyHUDEditor parent){
        this.parent = parent;
        this.data = new JsonObject();
        reload();

    }
    public void reload(){
        JsonObject obj = SealyModLangManager.SEALYHUD_DOCS.getAll();

        if (obj.isJsonObject()) {
            this.data = obj;
            this.keys = obj.keySet().stream().sorted().toList();
        } //else {
            //this.data.add(SealyModLangManager.MAIN.get("sealymod.sealyhud.editor.help.error"));
        //

    }

    void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta){
        graphics.fill(20,20, parent.width-20,parent.height-20,0x000000FF);
        for (String key : keys){
            JsonObject dat = this.data.get(key).getAsJsonObject();
            String desc = dat.get("desc").getAsString();
            String example = dat.get("example").getAsString();
            Button expand = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.help.expandBtn"),(btn)->{}).build();



            //graphics.text(this.parent.getFont(),this.data.get(i),20, (int) (20*i+20-parent.scrollAmount),0xFFFFFFFF);
        }

    }

    private class SubHelp{
        String key;
        String name;
        String desc;
        String example;
        Button expand;
        boolean expanded = false;
        SubHelp(String key){
            this.key = key;
            update();
        }
        void update(){
            JsonObject dat = data.get(key).getAsJsonObject();
            name = dat.get("name").getAsString();
            desc = dat.get("desc").getAsString();
            example = dat.get("example").getAsString();
            expand = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.help.expandBtn"),(btn)->{expanded = !expanded; update();}).build();
        }
        float render(GuiGraphicsExtractor graphics, float y){
            graphics.text(parent.getFont(),name,20, (int) y,0xFFFFFFFF);
            expand.setPosition(parent.width-expand.getWidth()-20,(int) y);
            if (expanded) {
                return 0;
            } else {
                return y + 22;
            }
        }
    }
}
