

package com.thelinuxseal.sealymod.client.config.categories.sealyhud;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import com.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import com.thelinuxseal.sealymod.client.sealyhud.SealyHUDElement;
import com.thelinuxseal.sealymod.client.sealyhud.SealyHUDElementManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class ConfigSealyHUDWidgetsScreen extends Screen {
    private final Screen parent;
    private final SealyModConfig config;
    private final List<WidgetRowEntry> UIEntries = new ArrayList();
    private double scrollAmount = (double)0.0F;
    private final int rowSpacing = 52;
    private final int topClipY = 40;
    private final int bottomClipY = 50;
    private ArrayList<SealyHUDElement> localWidgetData;
    private boolean saved = true;

    public ConfigSealyHUDWidgetsScreen(Screen parent, SealyModConfig config) {
        super(Component.literal("SealyHUD Element Dashboard"));
        this.parent = parent;
        this.config = config;
    }

    private void reload() {
        this.clearWidgets();
        this.UIEntries.clear();
        int startY = topClipY;

        for(int i = 0; i < this.localWidgetData.size(); ++i) {
            SealyHUDElement element = (SealyHUDElement)this.localWidgetData.get(i);
            int initialY = startY + i * 47;
            int fullWidth = this.width - 40;

            Checkbox checkbox = Checkbox.builder(Component.literal("Enabled"), this.font).onValueChange((cb, value) -> element.setEnabled(value)).pos(20, initialY).selected(element.isEnabled()).build();
            Button deleteBtn = Button.builder(Component.literal("❌ Delete"), (btn) -> {
                this.localWidgetData.remove(element);
                this.reload();
            }).bounds(this.width - 85, initialY, 65, 20).build();
            int xyInputWidth = fullWidth - checkbox.getWidth() - 69;
            EditBox xInput = new EditBox(this.font, checkbox.getWidth()+22, initialY, (xyInputWidth-2)/2, 20, Component.literal("X Formula"));
            xInput.setValue(element.getXFormula());
            EditBox yInput = new EditBox(this.font, checkbox.getWidth()+(xyInputWidth-2)/2+24, initialY, (xyInputWidth-2)/2, 20, Component.literal("Y Formula"));
            yInput.setValue(element.getYFormula());


            EditBox textInput = new EditBox(this.font, 20, initialY + 22, fullWidth, 20, Component.literal("Text"));
            textInput.setValue(element.getTextTemplate());

            xInput.setResponder(element::setXFormula);
            yInput.setResponder(element::setYFormula);
            textInput.setResponder(element::setTextTemplate);

            this.addRenderableWidget(checkbox);
            this.addRenderableWidget(xInput);
            this.addRenderableWidget(yInput);
            this.addRenderableWidget(deleteBtn);
            this.addRenderableWidget(textInput);
            this.UIEntries.add(new WidgetRowEntry(element, checkbox, xInput, yInput, textInput, deleteBtn, initialY));
        }

        this.addRenderableWidget(Button.builder(Component.literal("➕ Add Widget"), (btn) -> {
            this.saveValToTarget();
            this.localWidgetData.add(new SealyHUDElement("", "", "", false));
            this.reload();
        }).bounds(20, this.height - 35, 100, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Save"), (btn) -> this.applyAndSave()).bounds(this.width - 230, this.height - 35, 100, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("Exit"), (btn) -> this.minecraft.setScreen(this.parent)).bounds(this.width - 120, this.height - 35, 100, 20).build());
        this.updateWidgetPositions();
    }

    protected void init() {
        if (this.saved) {
            this.localWidgetData = new ArrayList();
            for (SealyHUDElement e : this.config.hudWidgets) {
                this.localWidgetData.add(new SealyHUDElement(e.getXFormula(), e.getYFormula(), e.getTextTemplate(), e.isEnabled()));
            }
            this.saved = false;
        }
        this.reload();
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int totalContentHeight = this.localWidgetData.size() * 52;
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
            entry.checkbox.setY(newY);
            entry.xInput.setY(newY);
            entry.yInput.setY(newY);
            entry.deleteBtn.setY(newY);
            entry.textInput.setY(newY + 22);
            boolean isVisible = newY >= topClipY && newY + 42 <= bottomLimit;
            entry.checkbox.visible = isVisible;
            entry.xInput.visible = isVisible;
            entry.yInput.visible = isVisible;
            entry.deleteBtn.visible = isVisible;
            entry.textInput.visible = isVisible;
            entry.xInput.setEditable(isVisible);
            entry.yInput.setEditable(isVisible);
            entry.textInput.setEditable(isVisible);
        }

    }

    private void saveValToTarget() {
        for(WidgetRowEntry entry : this.UIEntries) {
            entry.targetElement.setEnabled(entry.checkbox.selected());
            entry.targetElement.setXFormula(entry.xInput.getValue());
            entry.targetElement.setYFormula(entry.yInput.getValue());
            entry.targetElement.setTextTemplate(entry.textInput.getValue());
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
        graphics.fill(0, 0, this.width, 40, -15592942);
        graphics.text(this.font, "HUD Elements Master Control Panel", 20, 15, 16777215, true);
        graphics.fill(0, this.height - 50 + 15, this.width, this.height, -15592942);
    }
    private record WidgetRowEntry(SealyHUDElement targetElement, Checkbox checkbox, EditBox xInput, EditBox yInput, EditBox textInput, Button deleteBtn, int baseY) {}
}



