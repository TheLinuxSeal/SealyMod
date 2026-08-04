package io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs;

import io.github.thelinuxseal.sealymod.client.SealyModClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.MainContext;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.HudEditor;

import java.util.ArrayList;
import java.util.List;

import static io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.HudEditor.bottomClipY;
import static io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.HudEditor.topClipY;

public class HudDocScreen extends Screen {

    protected final HudEditor parent;

    //public final List<Map<String,String>> entries = new ArrayList<>();
    public List<HudDocScreenNode> nodes = new ArrayList<>();

    private double scrollAmount;

    protected float arrowWidth;

    private int totalContentHeight;

    private float scale;

    public HudDocScreen(HudEditor parent) {
        super(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.docs.title"));
        this.parent = parent;
        scale = 1;
        reload();
    }

    @Override
    protected void init() {
        Button exitHelpBtn = Button.builder(
                SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.docs.exit"),
                b -> Minecraft.getInstance().setScreenAndShow(parent)
        ).bounds(width - 120, height - 35, 100, 20).build();
        Button sizeMoreBtn = Button.builder(
                SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.docs.size.more"),
                b -> {scale = scale / 0.9F;}
        ).bounds(20, height - 35, 20, 20).build();
        Button sizeLessBtn = Button.builder(
                SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.docs.size.less"),
                b -> {scale = scale * 0.9F;}
        ).bounds(22+sizeMoreBtn.getWidth(), height - 35, 20, 20).build();

        addRenderableWidget(exitHelpBtn);
        addRenderableWidget(sizeMoreBtn);
        addRenderableWidget(sizeLessBtn);
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

        graphics.enableScissor(20,20,width-20,height-50);
        graphics.pose().pushMatrix();
        graphics.pose().scale(scale);

        Position pos =  new Position((20/scale)+10, (float) ((20/scale) + 10 - scrollAmount));
        for (HudDocScreenNode n : nodes){
            pos = n.render(graphics, (int) (mouseX*scale), (int) (mouseY*scale),delta,pos);
        }
        totalContentHeight = 1000000;
        graphics.pose().popMatrix();
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