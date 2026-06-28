

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
    private final List<WidgetRowEntry> UIEntries = new ArrayList<>();
    double scrollAmount = 0.0;
    private final int rowSpacing = 52;
    private final int topClipY = 20;
    private final int bottomClipY = 50;
    private ArrayList<SealyHUDElement> localWidgetData;
    private boolean saved = true;
    private boolean isHelpScreen = false;
    private SealyHUDEditorHelpScreen helpScreen;
    private Button addNewBtn;
    private Button helpBtn;
    private Button saveBtn;
    private Button exitBtn;
    private Button exitHelpBtn;

    public SealyHUDEditor(Screen parent, SealyModConfig config) {
        super(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.title"));
        this.parent = parent;
        this.config = config;
        this.helpScreen = new SealyHUDEditorHelpScreen(this);
    }

    private void reload() {
        this.clearWidgets();
        this.UIEntries.clear();

        for(int i = 0; i < this.localWidgetData.size(); ++i) {
            SealyHUDElement element = (SealyHUDElement)this.localWidgetData.get(i);
            int initialY = topClipY + i * rowSpacing;
            int fullWidth = this.width - 40;


            Checkbox checkbox = Checkbox.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.enableCheckbox"), this.font).onValueChange((cb, value) -> element.setEnabled(value)).pos(20, initialY+1).selected(element.isEnabled()).build();
            Button deleteBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.deleteButton"), (btn) -> {
                this.localWidgetData.remove(element);
                this.reload();
            }).bounds(this.width - 85, initialY, 65, 20).build();
            int intInputsWidth = fullWidth - checkbox.getWidth() - 71;
            EditBox xInput = new EditBox(this.font, checkbox.getWidth()+22, initialY, (int)((intInputsWidth -2)*0.4F), 20, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.xFormula"));
            xInput.setValue(element.getXFormula());
            EditBox yInput = new EditBox(this.font, (int)(checkbox.getWidth()+(intInputsWidth-2)*0.4F+24), initialY, (int)((intInputsWidth -2)*0.4F), 20, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.yFormula"));
            yInput.setValue(element.getYFormula());
            EditBox textSizeInput = new EditBox(this.font, (int)(checkbox.getWidth()+(intInputsWidth-2)*0.8F+26), initialY, (int)((intInputsWidth -2)*0.2F), 20, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.textSizeFormula"));
            textSizeInput.setValue(element.getTextSizeFormula());

            //Checkbox advancedParseCheckbox = Checkbox.builder(SealyModLangOld.getAsComponent("sealymod.config.sealyhud.editor.widget.advancedModeCheckbox"), this.font).onValueChange((cb, value) -> element.setAdvancedParseMode(value)).pos(20, initialY+23).selected(element.getAdvancedParseMode()).build();
            EditBox textInput = new EditBox(this.font, 20, initialY + 22, fullWidth, 20, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.textInput"));
            textInput.setMaxLength(4096);
            textInput.setValue(element.getTextTemplate());

            xInput.setResponder(element::setXFormula);
            yInput.setResponder(element::setYFormula);
            textSizeInput.setResponder(element::setTextSizeFormula);
            textInput.setResponder(element::setTextTemplate);
            this.addWidget(checkbox);
            this.addWidget(xInput);
            this.addWidget(yInput);
            this.addWidget(textSizeInput);
            this.addWidget(deleteBtn);
            this.addWidget(textInput);
            this.UIEntries.add(new WidgetRowEntry(element, checkbox, xInput, yInput, textSizeInput, textInput, deleteBtn, initialY));
        }

        this.addNewBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.addWidgetButton"), (btn) -> {
            this.saveValToTarget();
            this.localWidgetData.add(new SealyHUDElement("", "", "0.5", "", false));
            this.reload();
        }).bounds(20, this.height - 35, 100, 20).build();
        this.helpBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.help"), (btn) -> setHelpScreen(true)).bounds(this.width - 340, this.height - 35, 100, 20).build();
        this.exitBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.exit"), (btn) -> this.minecraft.setScreenAndShow(this.parent)).bounds(this.width - 120, this.height - 35, 100, 20).build();
        this.saveBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.save"), (btn) -> this.applyAndSave()).bounds(this.width - 230, this.height - 35, 100, 20).build();
        this.exitHelpBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.exitHelp"), (btn) -> setHelpScreen(false)).bounds(this.width - 120, this.height - 35, 100, 20).build();

        if (this.isHelpScreen) {
            this.addRenderableWidget(this.exitHelpBtn);
        } else {
            this.addRenderableWidget(this.addNewBtn);
            this.addRenderableWidget(this.helpBtn);
            this.addRenderableWidget(this.saveBtn);
            this.addRenderableWidget(this.exitBtn);
        }

        this.updateWidgetPositions();
    }

    protected void init() {
        if (this.saved) {
            this.localWidgetData = new ArrayList<>();
            for (SealyHUDElement e : this.config.sealyHUD.hudWidgets) {
                this.localWidgetData.add(new SealyHUDElement(e.getXFormula(), e.getYFormula(), e.getTextSizeFormula(), e.getTextTemplate(), e.isEnabled()));
            }
            this.saved = false;
        }
        this.reload();
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int totalContentHeight = this.localWidgetData.size() * rowSpacing;
        int viewHeight = this.height - bottomClipY - topClipY;
        int maxScroll = Math.max(0, totalContentHeight - viewHeight + 20);
        this.scrollAmount = Mth.clamp(this.scrollAmount - scrollY * (double)15.0F, (double)0.0F, (double)maxScroll);
        this.updateWidgetPositions();
        return true;
    }

    private void updateWidgetPositions() {
        int currentScroll = (int)this.scrollAmount;
        int bottomLimit = this.height - bottomClipY;

        for(WidgetRowEntry entry : this.UIEntries) {
            int newY = entry.baseY - currentScroll;
            entry.checkbox.setY(newY+1);
            entry.xInput.setY(newY);
            entry.yInput.setY(newY);
            entry.textSizeInput.setY(newY);
            entry.deleteBtn.setY(newY);
            entry.textInput.setY(newY + 22);
            boolean editTop = newY > topClipY-20 && newY < bottomLimit && !this.isHelpScreen;
            boolean editBottom = newY + 22 > topClipY-20 && newY + 22 < bottomLimit && !this.isHelpScreen;
            entry.xInput.setEditable(editTop);
            entry.yInput.setEditable(editTop);
            entry.textSizeInput.setEditable(editTop);
            if (!editTop){
                if (entry.xInput.isFocused()){
                    entry.xInput.setFocused(false);
                    this.setFocused(false);
                }
                if (entry.yInput.isFocused()){
                    entry.yInput.setFocused(false);
                    this.setFocused(false);
                }
                if (entry.textSizeInput.isFocused()){
                    entry.textSizeInput.setFocused(false);
                    this.setFocused(false);
                }
            }
            entry.textInput.setEditable(editBottom);
            if (!editBottom){
                if (entry.textInput.isFocused()){
                    entry.textInput.setFocused(false);
                    this.setFocused(false);
                }
            }
        }


    }

    private void saveValToTarget() {
        for(WidgetRowEntry entry : this.UIEntries) {
            entry.targetElement.setEnabled(entry.checkbox.selected());
            entry.targetElement.setXFormula(entry.xInput.getValue());
            entry.targetElement.setYFormula(entry.yInput.getValue());
            entry.targetElement.setTextSizeFormula(entry.textSizeInput.getValue());
            entry.targetElement.setTextTemplate(entry.textInput.getValue());
            //entry.targetElement.compile();
        }

    }

    private void applyAndSave() {
        this.saveValToTarget();
        this.config.sealyHUD.hudWidgets.clear();
        this.config.sealyHUD.hudWidgets.addAll(this.localWidgetData);
        SealyModConfigHandler.save();
        SealyHUDElementManager.loadFromConfig(this.config.sealyHUD.hudWidgets);
        this.saved = true;
        this.init();
    }

    private void setHelpScreen(boolean x){
        this.isHelpScreen = x;
        if (x) {
            this.removeWidget(addNewBtn);
            this.removeWidget(helpBtn);
            this.removeWidget(saveBtn);
            this.removeWidget(exitBtn);
            this.addRenderableWidget(exitHelpBtn);
        } else {
            this.addRenderableWidget(addNewBtn);
            this.addRenderableWidget(helpBtn);
            this.addRenderableWidget(saveBtn);
            this.addRenderableWidget(exitBtn);
            this.removeWidget(exitHelpBtn);
        }
        updateWidgetPositions();
    }


    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        //graphics.fill(0, 0, this.width, 40, -15592942);
        if (!isHelpScreen) {
            graphics.centeredText(this.font, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.title"), this.width/2, 5, 0xFFFFFFFF);
            graphics.enableScissor(20, topClipY, this.width - 20, this.height - bottomClipY);
            for (WidgetRowEntry entry : this.UIEntries) {
                entry.checkbox.extractRenderState(graphics, mouseX, mouseY, delta);
                entry.xInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
                entry.yInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
                entry.textSizeInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
                entry.textInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
                entry.deleteBtn.extractRenderState(graphics, mouseX, mouseY, delta);
            }
            graphics.disableScissor();
        } else {
            this.helpScreen.render(graphics, mouseX, mouseY, delta);
        }
        //graphics.fill(0, this.height - 50 + 15, this.width, this.height, -15592942);
    }
    private record WidgetRowEntry(SealyHUDElement targetElement, Checkbox checkbox, EditBox xInput, EditBox yInput, EditBox textSizeInput, EditBox textInput, Button deleteBtn, int baseY) {}
}



