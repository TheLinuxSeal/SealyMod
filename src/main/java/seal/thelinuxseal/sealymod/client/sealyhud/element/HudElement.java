package seal.thelinuxseal.sealymod.client.sealyhud.element;

import seal.thelinuxseal.sealymod.client.sealyhud.parser.HudExprParser;
import seal.thelinuxseal.sealymod.client.sealyhud.parser.HudTextParser;
import net.objecthunter.exp4j.Expression;
import org.apache.commons.jexl3.JxltEngine;


public class HudElement {
    private String xFormula;
    private String yFormula;
    private String textTemplate;
    private String textSizeFormula;
    private boolean enabled;


    private transient Expression xExpr;
    private transient Expression yExpr;
    private transient Expression textSizeExpr;
    private transient JxltEngine.Expression jexlTextExpr;

    // Default constructor for serialization/initial default values
    public HudElement(String xFormula, String yFormula, String textSizeFormula, String textTemplate, boolean enabled) {
        this.xFormula = xFormula;
        this.yFormula = yFormula;
        this.textSizeFormula = textSizeFormula;
        this.textTemplate = textTemplate;
        this.enabled = enabled;
        compile();
    }

    public void compile() {
        this.xExpr = HudExprParser.build(xFormula,"0");
        this.yExpr = HudExprParser.build(yFormula,"0");
        this.textSizeExpr = HudExprParser.build(textSizeFormula,"0.75");
        this.jexlTextExpr = HudTextParser.createExpr(this.textTemplate);
    }

    public int getX() {
        if (xExpr == null) return 0;

        return (int) HudExprParser.eval(xExpr,0);
    }

    public int getY() {
        if (yExpr == null) return 0;

        return (int) HudExprParser.eval(yExpr,0);
    }
    public float getTextSize() {
        if (textSizeExpr == null) return 0.75F;

        return (float) HudExprParser.eval(textSizeExpr,0.75F);
    }

    public String getText() {
        return HudTextParser.getJexlText(jexlTextExpr);
    }

    // Getters and Setters needed for serialization and YACL bindings
    public String getXFormula() { return xFormula; }
    public void setXFormula(String formula) { this.xFormula=formula;}

    public String getYFormula() { return yFormula; }
    public void setYFormula(String formula) { this.yFormula=formula;}

    public String getTextSizeFormula() { return textSizeFormula;}
    public void setTextSizeFormula(String formula) { this.textSizeFormula=formula;}

    public String getTextTemplate() { return textTemplate; }
    public void setTextTemplate(String text) { this.textTemplate=text;}

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean isEnabled) { this.enabled=isEnabled;}
}