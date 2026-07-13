package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;
import seal.thelinuxseal.sealymod.client.sealyhud.element.SealyHUDElement;

public class SealyHUDEditorElement {
    protected SealyHUDElement element;
    private SealyHUDEditor parent;
    protected Checkbox checkbox;
    protected EditBox xInput;
    protected EditBox yInput;
    protected EditBox textSizeInput;
    protected EditBox textInput;
    protected Button deleteBtn;

    public SealyHUDEditorElement(SealyHUDElement element, SealyHUDEditor parent) {
        this.element = element;
        this.parent = parent;
        checkbox = Checkbox.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.enableCheckbox"), parent.getFont()).onValueChange((cb, value) -> element.setEnabled(value)).pos(20, 1).selected(element.isEnabled()).build();
        deleteBtn = Button.builder(SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.deleteButton"), (btn) -> {
            parent.elements.remove(this);
            parent.reload();
        }).bounds(parent.width - 85, 0, 65, 20).build();
        int fullWidth = parent.width - 40;
        int intInputsWidth = fullWidth - checkbox.getWidth() - 71;
        xInput = new EditBox(parent.getFont(), checkbox.getWidth()+22, 0, (int)((intInputsWidth -2)*0.4F), 20, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.xFormula"));
        xInput.setValue(element.getXFormula());
        yInput = new EditBox(parent.getFont(), (int)(checkbox.getWidth()+(intInputsWidth-2)*0.4F+24), 0, (int)((intInputsWidth -2)*0.4F), 20, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.yFormula"));
        yInput.setValue(element.getYFormula());
        textSizeInput = new EditBox(parent.getFont(), (int)(checkbox.getWidth()+(intInputsWidth-2)*0.8F+26), 0, (int)((intInputsWidth -2)*0.2F), 20, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.textSizeFormula"));
        textSizeInput.setValue(element.getTextSizeFormula());

        textInput = new EditBox(parent.getFont(), 20, 22, fullWidth, 20, SealyModLangManager.MAIN.getAsComponent("sealymod.sealyhud.editor.widget.textInput"));
        textInput.setMaxLength(4096);
        textInput.setValue(element.getTextTemplate());
        xInput.setResponder(element::setXFormula);
        yInput.setResponder(element::setYFormula);
        textSizeInput.setResponder(element::setTextSizeFormula);
        textInput.setResponder(element::setTextTemplate);
    }

    public int updateY(int newY){
        int bottomLimit = parent.height - parent.bottomClipY;
        checkbox.setY(newY+1);
        xInput.setY(newY);
        yInput.setY(newY);
        textSizeInput.setY(newY);
        deleteBtn.setY(newY);
        textInput.setY(newY + 22);
        boolean editTop = newY > parent.topClipY-20 && newY < bottomLimit;
        boolean editBottom = newY + 22 > parent.topClipY-20 && newY + 22 < bottomLimit;
        xInput.setEditable(editTop);
        yInput.setEditable(editTop);
        textSizeInput.setEditable(editTop);
        if (!editTop){
            if (xInput.isFocused()){
                xInput.setFocused(false);
                parent.setFocused(false);
            }
            if (yInput.isFocused()){
                yInput.setFocused(false);
                parent.setFocused(false);
            }
            if (textSizeInput.isFocused()){
                textSizeInput.setFocused(false);
                parent.setFocused(false);
            }
        }
        textInput.setEditable(editBottom);
        if (!editBottom){
            if (textInput.isFocused()){
                textInput.setFocused(false);
                parent.setFocused(false);
            }
        }
        return newY + getHeight();
    }

    public int getHeight(){
        return 44;
    }

    public void save(){
        element.setEnabled(checkbox.selected());
        element.setXFormula(xInput.getValue());
        element.setYFormula(yInput.getValue());
        element.setTextSizeFormula(textSizeInput.getValue());
        element.setTextTemplate(textInput.getValue());
    }

    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta){
        checkbox.extractRenderState(graphics, mouseX, mouseY, delta);
        xInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
        yInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
        textSizeInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
        textInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
        deleteBtn.extractRenderState(graphics, mouseX, mouseY, delta);
    }



}