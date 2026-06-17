

package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import seal.thelinuxseal.sealymod.client.config.SealyModConfig;
import seal.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
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
    private double scrollAmount = (double)0.0F;
    private final int rowSpacing = 52;
    private final int topClipY = 40;
    private final int bottomClipY = 50;
    private ArrayList<SealyHUDElement> localWidgetData;
    private boolean saved = true;
    private boolean helpScreen = false;

    public SealyHUDEditor(Screen parent, SealyModConfig config) {
        super(SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.title"));
        this.parent = parent;
        this.config = config;
    }

    private void reload() {
        this.clearWidgets();
        this.UIEntries.clear();
        int startY = topClipY;

        for(int i = 0; i < this.localWidgetData.size(); ++i) {
            SealyHUDElement element = (SealyHUDElement)this.localWidgetData.get(i);
            int initialY = startY + i * rowSpacing;
            int fullWidth = this.width - 40;


            Checkbox checkbox = Checkbox.builder(SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.widget.enableCheckbox"), this.font).onValueChange((cb, value) -> element.setEnabled(value)).pos(20, initialY+1).selected(element.isEnabled()).build();
            Button deleteBtn = Button.builder(SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.widget.deleteButton"), (btn) -> {
                this.localWidgetData.remove(element);
                this.reload();
            }).bounds(this.width - 85, initialY, 65, 20).build();
            int intInputsWidth = fullWidth - checkbox.getWidth() - 69;
            EditBox xInput = new EditBox(this.font, checkbox.getWidth()+22, initialY, (int)((intInputsWidth -2)*0.4F), 20, SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.widget.xFormula"));
            xInput.setValue(element.getXFormula());
            EditBox yInput = new EditBox(this.font, (int)(checkbox.getWidth()+(intInputsWidth-2)*0.4F+24), initialY, (int)((intInputsWidth -2)*0.4F), 20, SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.widget.yFormula"));
            yInput.setValue(element.getYFormula());
            EditBox textSizeInput = new EditBox(this.font, (int)(checkbox.getWidth()+(intInputsWidth-2)*0.8F+26), initialY, (int)((intInputsWidth -2)*0.2F), 20, SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.widget.textSizeFormula"));
            textSizeInput.setValue(element.getTextSizeFormula());

            //Checkbox advancedParseCheckbox = Checkbox.builder(SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.widget.advancedModeCheckbox"), this.font).onValueChange((cb, value) -> element.setAdvancedParseMode(value)).pos(20, initialY+23).selected(element.getAdvancedParseMode()).build();
            EditBox textInput = new EditBox(this.font, 20, initialY + 22, fullWidth, 20, SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.widget.textInput"));
            textInput.setMaxLength(4096);
            textInput.setValue(element.getTextTemplate());

            xInput.setResponder(element::setXFormula);
            yInput.setResponder(element::setYFormula);
            textSizeInput.setResponder(element::setTextSizeFormula);
            textInput.setResponder(element::setTextTemplate);

            this.addRenderableWidget(checkbox);
            this.addRenderableWidget(xInput);
            this.addRenderableWidget(yInput);
            this.addRenderableWidget(textSizeInput);
            this.addRenderableWidget(deleteBtn);
            //this.addRenderableWidget(advancedParseCheckbox);
            this.addRenderableWidget(textInput);
            this.UIEntries.add(new WidgetRowEntry(element, checkbox, xInput, yInput, textSizeInput, textInput, deleteBtn, initialY));
        }

        this.addRenderableWidget(Button.builder(SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.addWidgetButton"), (btn) -> {
            this.saveValToTarget();
            this.localWidgetData.add(new SealyHUDElement("", "", "0.5", "", false));
            this.reload();
        }).bounds(20, this.height - 35, 100, 20).build());
        this.addRenderableWidget(Button.builder(SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.save"), (btn) -> this.applyAndSave()).bounds(this.width - 230, this.height - 35, 100, 20).build());
        this.addRenderableWidget(Button.builder(SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.exit"), (btn) -> this.minecraft.setScreenAndShow(this.parent)).bounds(this.width - 120, this.height - 35, 100, 20).build());
        this.updateWidgetPositions();
    }

    protected void init() {
        if (this.saved) {
            this.localWidgetData = new ArrayList<>();
            for (SealyHUDElement e : this.config.hudWidgets) {
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
            boolean isVisible = newY >= topClipY && newY + 42 <= bottomLimit && !this.helpScreen;
            entry.checkbox.visible = isVisible;
            entry.xInput.visible = isVisible;
            entry.yInput.visible = isVisible;
            entry.textSizeInput.visible = isVisible;
            entry.deleteBtn.visible = isVisible;
            entry.textInput.visible = isVisible;
            entry.xInput.setEditable(isVisible);
            entry.yInput.setEditable(isVisible);
            entry.textSizeInput.setEditable(isVisible);
            entry.textInput.setEditable(isVisible);
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
        this.config.hudWidgets.clear();
        this.config.hudWidgets.addAll(this.localWidgetData);
        SealyModConfigHandler.save();
        SealyHUDElementManager.loadFromConfig(this.config.hudWidgets);
        this.saved = true;
        this.init();
    }

    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        //graphics.fill(0, 0, this.width, 40, -15592942);
        graphics.text(this.font, SealyModLang.getAsComponent("sealymod.config.sealyhud.editor.title"), 20, 15, 16777215, true);
        //graphics.fill(0, this.height - 50 + 15, this.width, this.height, -15592942);
    }
    private record WidgetRowEntry(SealyHUDElement targetElement, Checkbox checkbox, EditBox xInput, EditBox yInput, EditBox textSizeInput, EditBox textInput, Button deleteBtn, int baseY) {}
}



