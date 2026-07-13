

package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import org.jspecify.annotations.NonNull;
import seal.thelinuxseal.sealymod.client.config.data.SealyModConfig;
import seal.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;
import seal.thelinuxseal.sealymod.client.sealyhud.element.SealyHUDElement;
import seal.thelinuxseal.sealymod.client.sealyhud.element.SealyHUDElementManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

public class SealyHUDEditor extends Screen {
    private final Screen parent;
    private final SealyModConfig config;
    //private final List<WidgetRowEntry> UIEntries = new ArrayList<>();
    double scrollAmount = 0.0;
    protected final int topClipY = 20;
    protected final int bottomClipY = 50;
    //private ArrayList<SealyHUDElement> localWidgetData;
    protected List<SealyHUDEditorElement> elements;
    private boolean saved = true;
    private Button addNewBtn;
    private Button saveBtn;
    private Button exitBtn;
    private Button helpBtn;

    public SealyHUDEditor(Screen parent, SealyModConfig config) {
        super(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.title"));
        this.parent = parent;
        this.config = config;
    }

    void reload() {
        this.clearWidgets();

        for (SealyHUDEditorElement ee : elements) {
            this.addWidget(ee.checkbox);
            this.addWidget(ee.xInput);
            this.addWidget(ee.yInput);
            this.addWidget(ee.textSizeInput);
            this.addWidget(ee.deleteBtn);
            this.addWidget(ee.textInput);
        }




        this.addNewBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.addWidgetButton"), (btn) -> {
            this.elements.add(new SealyHUDEditorElement(new SealyHUDElement("", "", "0.75", "", false),this));
            this.reload();
        }).bounds(20, this.height - 35, 100, 20).build();
        this.exitBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.exit"), (btn) -> this.minecraft.setScreenAndShow(this.parent)).bounds(this.width - 120, this.height - 35, 100, 20).build();
        this.saveBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.save"), (btn) -> this.applyAndSave()).bounds(this.width - 230, this.height - 35, 100, 20).build();
        this.helpBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.help"), (btn) -> this.minecraft.setScreenAndShow(new SealyHUDEditorHelpScreen(this))).bounds(this.width - 340, this.height - 35, 100, 20).build();
        this.addRenderableWidget(this.addNewBtn);
        this.addRenderableWidget(this.saveBtn);
        this.addRenderableWidget(this.exitBtn);
        this.addRenderableWidget(this.helpBtn);


        this.updateWidgetPositions();
    }

    protected void init() {
        if (this.saved) {
            this.elements = new ArrayList<>();
            for (SealyHUDElement e : this.config.sealyHUD.hudWidgets) {
                SealyHUDEditorElement ee = new SealyHUDEditorElement(e,this);
                this.addWidget(ee.checkbox);
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
                .mapToInt(SealyHUDEditorElement::getHeight)
                .sum();
        int viewHeight = this.height - bottomClipY - topClipY;
        int maxScroll = Math.max(0, totalContentHeight - viewHeight + 20);
        this.scrollAmount = Mth.clamp(this.scrollAmount - scrollY * (double)15.0F, (double)0.0F, (double)maxScroll);
        this.updateWidgetPositions();
        return true;
    }

    private void updateWidgetPositions() {
        int currentScroll = (int)this.scrollAmount;
        int bottomLimit = this.height - bottomClipY;
        int y =  topClipY-currentScroll;

        for (SealyHUDEditorElement e : elements) {
                y = e.updateY(y);
            }
        }




    private void saveValToTarget() {
        for(SealyHUDEditorElement e : elements) {
            e.save();
        }

    }

    private void applyAndSave() {
        saveValToTarget();
        config.sealyHUD.hudWidgets.clear();
        for (SealyHUDEditorElement e : elements) {
            config.sealyHUD.hudWidgets.add(e.element);
        }
        SealyModConfigHandler.save();
        SealyHUDElementManager.loadFromConfig(this.config.sealyHUD.hudWidgets);
        this.saved = true;
        this.init();
    }



    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        //graphics.fill(0, 0, this.width, 40, -15592942);
        graphics.centeredText(this.font, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.title"), this.width/2, 5, 0xFFFFFFFF);
        graphics.enableScissor(20, topClipY, this.width - 20, this.height - bottomClipY);
        for (SealyHUDEditorElement e : elements) {
            e.extractRenderState(graphics, mouseX, mouseY, delta);
        }
        graphics.disableScissor();
        //graphics.fill(0, this.height - 50 + 15, this.width, this.height, -15592942);
    }
    private record WidgetRowEntry(SealyHUDElement targetElement, Checkbox checkbox, EditBox xInput, EditBox yInput, EditBox textSizeInput, EditBox textInput, Button deleteBtn, int baseY) {}

}




