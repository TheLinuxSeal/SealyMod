package io.github.thelinuxseal.sealymod.client.sealyhud.editor.docs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import io.github.thelinuxseal.sealymod.client.SealyModClient;
import io.github.thelinuxseal.sealymod.client.sealyhud.contexts.MainContext;
import io.github.thelinuxseal.sealymod.client.sealyhud.editor.HudEditor;

import java.util.ArrayList;
import java.util.List;

import static io.github.thelinuxseal.sealymod.client.sealyhud.editor.HudEditor.bottomClipY;
import static io.github.thelinuxseal.sealymod.client.sealyhud.editor.HudEditor.topClipY;

public class HudDocScreen extends Screen {

    protected final HudEditor parent;

    //public final List<Map<String,String>> entries = new ArrayList<>();
    public List<HudDocScreenNode> nodes = new ArrayList<>();

    private double scrollAmount;

    protected float arrowWidth;

    private int totalContentHeight;

    public HudDocScreen(HudEditor parent) {
        super(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.docs.title"));
        this.parent = parent;
        reload();
    }

    @Override
    protected void init() {
        Button exitHelpBtn = Button.builder(
                SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.docs.exit"),
                b -> Minecraft.getInstance().setScreenAndShow(parent)
        ).bounds(width - 120, height - 35, 100, 20).build();

        addRenderableWidget(exitHelpBtn);
    }

    private void reload() {
        //entries.clear();
        nodes.clear();
        nodes.add(HudDocScanner.scan("context", MainContext.self, this,"API"));
        nodes.add(HudDocScanner.scan("context", ExtraDocContext.self, this,"API Classes"));
        arrowWidth = font.getSplitter().stringWidth(" -> ");

        //;

        /*docs.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey()))
                .forEach(entry -> {
                    JsonObject obj = entry.getValue().getAsJsonObject();

                    entries.add(new HelpEntry(
                            obj.get("name").getAsString(),
                            obj.get("desc").getAsString(),
                            obj.get("example").getAsString()
                    ));
                });*/
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {



        super.extractRenderState(graphics, mouseX, mouseY, delta);

        graphics.fill(
                20,
                20,
                width - 20,
                height - 50,
                0xAA000000
        );

        int y = (int)(30 - scrollAmount);
        graphics.enableScissor(20,20,width-20,height-50);
        Position pos =  new Position(30, y);
        for (HudDocScreenNode n : nodes){
            pos = n.render(graphics,mouseX,mouseY,delta,pos);
        }
        totalContentHeight = 1000000;
        graphics.disableScissor();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int viewHeight = this.height - bottomClipY - topClipY;
        this.scrollAmount = Mth.clamp(this.scrollAmount - scrollY * (double)15.0F, 0.0F, Math.max(0, totalContentHeight - viewHeight + 20));
        return true;
    }

    public void addNewWidget(AbstractWidget w){
        this.addWidget(w);
    }
    protected record Position(float x, float y){}
}