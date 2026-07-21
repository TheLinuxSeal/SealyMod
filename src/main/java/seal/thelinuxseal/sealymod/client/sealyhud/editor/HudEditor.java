

package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import seal.thelinuxseal.sealymod.client.SealyModClient;
import seal.thelinuxseal.sealymod.client.config.data.RootConfig;
import seal.thelinuxseal.sealymod.client.config.ConfigHandler;
import seal.thelinuxseal.sealymod.client.sealyhud.element.HudElement;
import seal.thelinuxseal.sealymod.client.sealyhud.element.HudElementManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

import static seal.thelinuxseal.sealymod.client.sealyhud.editor.HudEditorConstants.bottomClipY;
import static seal.thelinuxseal.sealymod.client.sealyhud.editor.HudEditorConstants.topClipY;

public class HudEditor extends Screen {
    private final Screen parent;
    private final RootConfig config;
    //private final List<WidgetRowEntry> UIEntries = new ArrayList<>();
    double scrollAmount = 0.0;

    //private ArrayList<SealyHUDElement> localWidgetData;
    protected List<HudEditorElement> elements;
    private boolean saved = true;

    public HudEditor(Screen parent, RootConfig config) {
        super(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.title"));
        this.parent = parent;
        this.config = config;
    }

    void reload() {
        this.clearWidgets();

        for (HudEditorElement ee : elements) {
            this.addWidget(ee.toggleBtn);
            this.addWidget(ee.xInput);
            this.addWidget(ee.yInput);
            this.addWidget(ee.textSizeInput);
            this.addWidget(ee.deleteBtn);
            this.addWidget(ee.textInput);
        }

        Component addNewStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.addWidgetButton");
        Component exitStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.exit");
        Component saveStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.save");
        Component helpStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.help");
        int width = Math.max(Math.max(this.font.width(addNewStr),this.font.width(exitStr)),Math.max(this.font.width(saveStr),this.font.width(helpStr)))+16;
        Button addNewBtn = Button.builder(addNewStr, (btn) -> {
            this.elements.add(new HudEditorElement(new HudElement("", "", "", "", false), this));
            this.reload();
        }).bounds(20, this.height - 35, width, 20).build();
        Button exitBtn = Button.builder(exitStr, (btn) -> this.minecraft.setScreenAndShow(this.parent)).bounds(this.width - width - 20, this.height - 35, width, 20).build();
        Button saveBtn = Button.builder(saveStr, (btn) -> this.applyAndSave()).bounds(this.width - 2*width - 22, this.height - 35, width, 20).build();
        Button helpBtn = Button.builder(helpStr, (btn) -> this.minecraft.setScreenAndShow(new HudHelpScreen(this))).bounds(this.width - 3*width - 24, this.height - 35, width, 20).build();
        this.addRenderableWidget(addNewBtn);
        this.addRenderableWidget(saveBtn);
        this.addRenderableWidget(exitBtn);
        this.addRenderableWidget(helpBtn);


        this.updateWidgetPositions();
    }

    protected void init() {
        if (this.saved) {
            this.elements = new ArrayList<>();
            for (HudElement e : this.config.sealyHud.widgets) {
                HudEditorElement ee = new HudEditorElement(e,this);
                this.addWidget(ee.toggleBtn);
                this.addWidget(ee.xInput);
                this.addWidget(ee.yInput);
                this.addWidget(ee.textSizeInput);
                this.addWidget(ee.deleteBtn);
                this.addWidget(ee.textInput);
                this.elements.add(ee);
            }
            this.saved = false;
        }
        this.reload();
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int totalContentHeight = elements.stream()
                .mapToInt(HudEditorElement::getHeight)
                .sum();
        int viewHeight = this.height - bottomClipY - topClipY;
        int maxScroll = Math.max(0, totalContentHeight - viewHeight + 20);
        this.scrollAmount = Mth.clamp(this.scrollAmount - scrollY * (double)15.0F, (double)0.0F, (double)maxScroll);
        this.updateWidgetPositions();
        return true;
    }

    private void updateWidgetPositions() {
        int currentScroll = (int)this.scrollAmount;
        int y =  topClipY-currentScroll;

        for (HudEditorElement e : elements) {
                y = e.updatePos(y);
            }
        }




    private void saveValToTarget() {
        for(HudEditorElement e : elements) {
            e.save();
        }

    }

    private void applyAndSave() {
        saveValToTarget();
        config.sealyHud.widgets.clear();
        for (HudEditorElement e : elements) {
            config.sealyHud.widgets.add(e.element);
        }
        ConfigHandler.save();
        HudElementManager.loadFromConfig(this.config.sealyHud.widgets);
        this.saved = true;
        this.init();
    }



    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        //graphics.fill(0, 0, this.width, 40, -15592942);
        graphics.centeredText(this.font, SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.title"), this.width/2, 5, 0xFFFFFFFF);
        graphics.enableScissor(20, topClipY, this.width - 20, this.height - bottomClipY);
        int currentScroll = (int)this.scrollAmount;
        int y =  topClipY-currentScroll;
        for (HudEditorElement e : elements) {
            y = e.extractRenderState(graphics, mouseX, mouseY, delta, y);
        }
        graphics.disableScissor();
        //graphics.fill(0, this.height - 50 + 15, this.width, this.height, -15592942);
    }

}




