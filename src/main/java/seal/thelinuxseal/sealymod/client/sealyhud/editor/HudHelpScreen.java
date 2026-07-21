package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import seal.thelinuxseal.sealymod.client.SealyModClient;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.MainContext;
import seal.thelinuxseal.sealymod.client.sealyhud.docs.ExtraDocContext;
import seal.thelinuxseal.sealymod.client.sealyhud.docs.HudContextScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static seal.thelinuxseal.sealymod.client.sealyhud.editor.HudEditorConstants.bottomClipY;
import static seal.thelinuxseal.sealymod.client.sealyhud.editor.HudEditorConstants.topClipY;

public class HudHelpScreen extends Screen {

    private final HudEditor parent;

    private Button exitHelpBtn;

    public final List<Map<String,String>> entries = new ArrayList<>();

    private double scrollAmount;

    private float arrowWidth;

    private int totalContentHeight;

    public HudHelpScreen(HudEditor parent) {
        super(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.help.title"));
        this.parent = parent;
        reload();
    }

    @Override
    protected void init() {
        exitHelpBtn = Button.builder(
                SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.help.exit"),
                b -> Minecraft.getInstance().setScreenAndShow(parent)
        ).bounds(width - 120, height - 35, 100, 20).build();

        addRenderableWidget(exitHelpBtn);
    }

    private void reload() {
        entries.clear();
        HudContextScanner.scan("context", MainContext.self, this);
        HudContextScanner.scan("context", ExtraDocContext.self, this);
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
        for (Map<String,String> entry : entries) {
            y = renderEntry(graphics,y,entry);

        }
        totalContentHeight = y;
        graphics.disableScissor();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int viewHeight = this.height - bottomClipY - topClipY;
        this.scrollAmount = Mth.clamp(this.scrollAmount - scrollY * (double)15.0F, 0.0F, Math.max(0, totalContentHeight - viewHeight + 20));
        return true;
    }

    private int renderEntry(GuiGraphicsExtractor graphics, int startY, Map<String,String> entry) {
        int y = startY;
        graphics.text(
                font,
                Component.literal(entry.get("name")).withStyle(style -> style.withBold(true)),
                30,
                y,
                0xFFFFFFFF
        );

        y += 14;

        graphics.text(
                font,
                entry.get("desc"),
                40,
                y,
                0xFFCCCCCC
        );


        y += 14;

        graphics.text(
                font,
                entry.get("path"),
                40,
                y,
                0xFF55FF55
        );
        graphics.text(
                font,
                " -> ",
                (int) (40+font.getSplitter().stringWidth(entry.get("path"))),
                y,
                0xFFAAAAAA
        );
        graphics.text(
                font,
                entry.get("returns"),
                (int) (40+font.getSplitter().stringWidth(entry.get("path"))+arrowWidth),
                y,
                0xFFFFFF55
        );



        y += 20;
        return y;
    }

}