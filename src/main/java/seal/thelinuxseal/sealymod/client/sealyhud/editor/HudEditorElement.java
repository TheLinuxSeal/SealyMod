package seal.thelinuxseal.sealymod.client.sealyhud.editor;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import seal.thelinuxseal.sealymod.client.SealyModClient;
import seal.thelinuxseal.sealymod.client.sealyhud.element.HudElement;

import static seal.thelinuxseal.sealymod.client.sealyhud.editor.HudEditorConstants.bottomClipY;
import static seal.thelinuxseal.sealymod.client.sealyhud.editor.HudEditorConstants.topClipY;

public class HudEditorElement {
    protected HudElement element;
    private HudEditor parent;
    //protected Checkbox checkbox;
    private boolean isEnabled;
    protected Button toggleBtn;
    protected EditBox xInput;
    protected EditBox yInput;
    protected EditBox textSizeInput;
    protected EditBox textInput;
    protected Button deleteBtn;

    private int toggleBtnWidth;
    private int deleteBtnWidth;

    public HudEditorElement(HudElement element, HudEditor parent) {
        this.element = element;
        this.parent = parent;
        isEnabled = element.isEnabled();
        //checkbox = Checkbox.builder(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.enableCheckbox"), parent.getFont()).onValueChange((cb, value) -> element.setEnabled(value)).pos(20, 1).selected(element.isEnabled()).build();
        Component enableStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.enabled");
        Component disableStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.disabled");
        Component currentStr = isEnabled ? enableStr : disableStr;
        toggleBtnWidth = Math.max(this.parent.getFont().width(enableStr),this.parent.getFont().width(disableStr))+8;
        toggleBtn = Button.builder(currentStr, (btn) -> {
            isEnabled = !isEnabled;
            btn.setMessage(isEnabled ? enableStr : disableStr);
        }).bounds(25, 0, toggleBtnWidth, 20).build();
        Component deleteStr = SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.deleteButton");
        deleteBtnWidth = this.parent.getFont().width(deleteStr)+8;
        deleteBtn = Button.builder(deleteStr, (btn) -> {
            parent.elements.remove(this);
            parent.reload();
        }).bounds(parent.width - deleteBtnWidth - 25, 0, deleteBtnWidth, 20).build();
        int fullWidth = parent.width - 40;
        xInput = new EditBox(parent.getFont(), 0, 0, fullWidth, 20, SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.xFormula"));
        xInput.setValue(element.getXFormula());
        yInput = new EditBox(parent.getFont(), 0, 0, fullWidth, 20, SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.yFormula"));
        yInput.setValue(element.getYFormula());
        textSizeInput = new EditBox(parent.getFont(), 0, 0, fullWidth, 20, SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.textSizeFormula"));
        textSizeInput.setValue(element.getTextSizeFormula());
        textInput = new EditBox(parent.getFont(), 0, 0, fullWidth, 20, SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.textInput"));
        textInput.setMaxLength(4096);
        textInput.setValue(element.getTextTemplate());

        xInput.setHint(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.xFormula"));
        yInput.setHint(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.yFormula"));
        textSizeInput.setHint(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.textSizeFormula"));
        textInput.setHint(SealyModClient.lang.getAsComponent("sealymod.sealyhud.editor.widget.textInput"));


        xInput.setResponder(element::setXFormula);
        yInput.setResponder(element::setYFormula);
        textSizeInput.setResponder(element::setTextSizeFormula);
        textInput.setResponder(element::setTextTemplate);
    }

    public int updatePos(int newY){

        int fullWidth = parent.width - 50;
        int intInputsWidth = fullWidth - toggleBtnWidth - deleteBtnWidth - 7;

        int posInputWidth = (int)(intInputsWidth * 0.4);
        int textSizeInputWidth = (int)(intInputsWidth * 0.2);
        int xInputX = toggleBtnWidth + 27;
        int yInputX = xInputX + posInputWidth + 2;
        int textSizeInputX = yInputX + posInputWidth + 2;
        int deleteBtnX = textSizeInputX + textSizeInputWidth + 2;

        toggleBtn.setX(25);
        toggleBtn.setWidth(toggleBtnWidth);
        xInput.setX(xInputX);
        xInput.setWidth(posInputWidth);
        yInput.setX(yInputX);
        yInput.setWidth(posInputWidth);
        textSizeInput.setX(textSizeInputX);
        textSizeInput.setWidth(textSizeInputWidth);
        deleteBtn.setX(deleteBtnX);
        deleteBtn.setWidth(deleteBtnWidth);
        textInput.setX(25);
        textInput.setWidth(fullWidth);

        int bottomLimit = parent.height - bottomClipY;
        toggleBtn.setY(newY+5);
        xInput.setY(newY+5);
        yInput.setY(newY+5);
        textSizeInput.setY(newY+5);
        deleteBtn.setY(newY+5);
        textInput.setY(newY+27);
        boolean editTop = newY+5 > topClipY-20 && newY+5 < bottomLimit;
        boolean editBottom = newY+27 > topClipY-20 && newY+27 < bottomLimit;
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
        return 54;
    }



    public void save(){
        element.setEnabled(isEnabled);
        element.setXFormula(xInput.getValue());
        element.setYFormula(yInput.getValue());
        element.setTextSizeFormula(textSizeInput.getValue());
        element.setTextTemplate(textInput.getValue());
    }

    public int extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta, int y){
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath("minecraft", "widget/button"),20,y, parent.width-40, 52);
        toggleBtn.extractRenderState(graphics, mouseX, mouseY, delta);
        xInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
        yInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
        textSizeInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
        textInput.extractWidgetRenderState(graphics, mouseX, mouseY, delta);
        deleteBtn.extractRenderState(graphics, mouseX, mouseY, delta);
        return y + getHeight();
    }



}