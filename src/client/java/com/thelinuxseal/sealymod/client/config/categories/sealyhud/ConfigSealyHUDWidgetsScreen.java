package com.thelinuxseal.sealymod.client.config.categories.sealyhud;

import com.thelinuxseal.sealymod.client.config.SealyModConfig;
import com.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import com.thelinuxseal.sealymod.client.sealyhud.SealyHUDElement;
import com.thelinuxseal.sealymod.client.sealyhud.SealyHUDElementManager;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.ArrayList;
import java.util.List;

public class ConfigSealyHUDWidgetsScreen extends Screen {
    private final Screen parent;
    private final SealyModConfig config;
    private final List<WidgetRowEntry> UIEntries = new ArrayList<>();

    private double scrollAmount = 0;
    // Tightened spacing to match 2 clean rows perfectly
    private final int rowSpacing = 52;
    private final int topClipY = 40;
    private final int bottomClipY = 50;

    public ConfigSealyHUDWidgetsScreen(Screen parent, SealyModConfig config) {
        super(Component.literal("SealyHUD Element Dashboard"));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        this.clearWidgets();
        this.UIEntries.clear();

        int startY = topClipY + 10;

        for (int i = 0; i < config.hudWidgets.size(); i++) {
            SealyHUDElement element = config.hudWidgets.get(i);
            int initialY = startY + (i * rowSpacing);

            // LINE 1: Horizontal Utility Settings Group
            Checkbox checkbox = Checkbox.builder(Component.literal("Enabled"), this.font)
                    .pos(20, initialY)
                    .selected(element.isEnabled())
                    .build();

            EditBox xInput = new EditBox(this.font, 95, initialY, 100, 20, Component.literal("X Formula"));
            xInput.setValue(element.getXFormula());

            EditBox yInput = new EditBox(this.font, 200, initialY, 100, 20, Component.literal("Y Formula"));
            yInput.setValue(element.getYFormula());

            // Delete sits anchored perfectly against the right border line
            Button deleteBtn = Button.builder(Component.literal("❌ Delete"), btn -> {
                config.hudWidgets.remove(element);
                SealyHUDElementManager.loadFromConfig(config.hudWidgets);
                this.init();
            }).bounds(this.width - 85, initialY, 65, 20).build();

            // LINE 2: Text Template (Calculates width dynamically to scale with the screen window boundary)
            int fullTextInputWidth = this.width - 40;
            EditBox textInput = new EditBox(this.font, 20, initialY + 22, fullTextInputWidth, 20, Component.literal("Text"));
            textInput.setValue(element.getTextTemplate());

            this.addRenderableWidget(checkbox);
            this.addRenderableWidget(xInput);
            this.addRenderableWidget(yInput);
            this.addRenderableWidget(deleteBtn);
            this.addRenderableWidget(textInput);

            UIEntries.add(new WidgetRowEntry(element, checkbox, xInput, yInput, textInput, deleteBtn, initialY));
        }

        // Bottom Screen Operations Frame Bar
        this.addRenderableWidget(Button.builder(Component.literal("➕ Add Widget"), btn -> {
            config.hudWidgets.add(new SealyHUDElement("", "", "", false));
            SealyHUDElementManager.loadFromConfig(config.hudWidgets);
            this.init();
        }).bounds(20, this.height - 35, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Save & Exit"), btn -> {
            this.applyAndSave();
            this.minecraft.setScreen(parent);
        }).bounds(this.width - 230, this.height - 35, 100, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Cancel"), btn -> {
            this.minecraft.setScreen(parent);
        }).bounds(this.width - 120, this.height - 35, 100, 20).build());

        this.updateWidgetPositions();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int totalContentHeight = config.hudWidgets.size() * rowSpacing;
        int viewHeight = (this.height - bottomClipY) - topClipY;
        int maxScroll = Math.max(0, totalContentHeight - viewHeight + 20);

        this.scrollAmount = Mth.clamp(this.scrollAmount - (scrollY * 15), 0, maxScroll);
        this.updateWidgetPositions();
        return true;
    }

    private void updateWidgetPositions() {
        int currentScroll = (int) this.scrollAmount;
        int bottomLimit = this.height - bottomClipY;

        for (WidgetRowEntry entry : UIEntries) {
            int newY = entry.baseY - currentScroll;

            // Anchor updates to our custom 2-line offset grid
            entry.checkbox.setY(newY);
            entry.xInput.setY(newY);
            entry.yInput.setY(newY);
            entry.deleteBtn.setY(newY);
            entry.textInput.setY(newY + 22);

            // Safety check matches the bottom edge of line 2 (newY + 42)
            boolean isVisible = (newY >= topClipY && (newY + 42) <= bottomLimit);

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

    private void applyAndSave() {
        for (WidgetRowEntry entry : UIEntries) {
            entry.targetElement.setEnabled(entry.checkbox.selected());
            entry.targetElement.setXFormula(entry.xInput.getValue());
            entry.targetElement.setYFormula(entry.yInput.getValue());
            entry.targetElement.setTextTemplate(entry.textInput.getValue());
        }
        SealyHUDElementManager.loadFromConfig(config.hudWidgets);
        SealyModConfigHandler.save();
    }

    public void render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        // Top HUD Header Canvas Mask layer
        graphics.fill(0, 0, this.width, topClipY, 0xFF121212);
        graphics.text(this.font, "HUD Elements Master Control Panel", 20, 15, 0xFFFFFF, true);

        // Bottom HUD Footer Action Mask layer
        graphics.fill(0, this.height - bottomClipY + 15, this.width, this.height, 0xFF121212);
    }

    private record WidgetRowEntry(SealyHUDElement targetElement, Checkbox checkbox, EditBox xInput, EditBox yInput, EditBox textInput, Button deleteBtn, int baseY) {}
}