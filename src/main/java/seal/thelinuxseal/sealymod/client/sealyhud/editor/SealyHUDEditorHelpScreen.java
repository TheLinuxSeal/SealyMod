package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.MainContext;
import seal.thelinuxseal.sealymod.client.sealyhud.docs.SealyHUDContextScanner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SealyHUDEditorHelpScreen extends Screen {

    private final SealyHUDEditor parent;

    private Button exitHelpBtn;

    public final List<HelpEntry> entries = new ArrayList<>();

    private double scrollAmount;

    public SealyHUDEditorHelpScreen(SealyHUDEditor parent) {
        super(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.help.title"));
        this.parent = parent;
        reload();
    }

    @Override
    protected void init() {
        exitHelpBtn = Button.builder(
                SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.help.exit"),
                b -> Minecraft.getInstance().setScreenAndShow(parent)
        ).bounds(width - 120, height - 35, 100, 20).build();

        addRenderableWidget(exitHelpBtn);
    }

    private void reload() {
        entries.clear();
        SealyHUDContextScanner.scan("context", MainContext.self, this);

        //JsonObject docs = SealyModLangManager.SEALYHUD_DOCS.getAll();

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
        for (HelpEntry entry : entries) {

            graphics.text(
                    font,
                    Component.literal(entry.name()).withStyle(style -> style.withBold(true)),
                    30,
                    y,
                    0xFFFFFFFF
            );

            y += 14;

            graphics.text(
                    font,
                    entry.desc(),
                    40,
                    y,
                    0xFFCCCCCC
            );


            y += 14;

            graphics.text(
                    font,
                    entry.path(),
                    40,
                    y,
                    0xFF55FF55
            );

            y += 20;
        }
        graphics.disableScissor();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        scrollAmount -= scrollY * 20;
        scrollAmount = Math.max(0, scrollAmount);
        return true;
    }

    public record HelpEntry(
            String name,
            String desc,
            String path
    ) {}
}