package seal.thelinuxseal.sealymod.client.sealyhud.element;

import seal.thelinuxseal.sealymod.client.sealyhud.parser.SealyHUDTextParser;
import net.minecraft.client.Minecraft;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.jexl3.JxltEngine;


public class SealyHUDElement {
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
    public SealyHUDElement(String xFormula, String yFormula, String textSizeFormula, String textTemplate, boolean enabled) {
        this.xFormula = xFormula;
        this.yFormula = yFormula;
        this.textSizeFormula = textSizeFormula;
        this.textTemplate = textTemplate;
        this.enabled = enabled;
        compile();
    }

    public void compile() {


        try {
            this.xExpr = new ExpressionBuilder(xFormula)
                    .variables("screenwidth", "screenheight")
                    .build();
        } catch (Exception e) {
            this.xExpr = new ExpressionBuilder("0").build();
        }
        try {
            this.yExpr = new ExpressionBuilder(yFormula)
                    .variables("screenwidth", "screenheight")
                    .build();
        } catch (Exception e) {
            this.yExpr = new ExpressionBuilder("0").build();
        }
        try {
            this.textSizeExpr = new ExpressionBuilder(textSizeFormula)
                    .variables("screenwidth", "screenheight")
                    .build();
        } catch (Exception e) {
            this.textSizeExpr = new ExpressionBuilder("0").build();
        }
        this.jexlTextExpr = SealyHUDTextParser.createExpr(this.textTemplate);
    }

    public int getX() {
        Minecraft client = Minecraft.getInstance();
        if (xExpr == null) return 0;

        try {
            return (int) xExpr
                    .setVariable("screenwidth", client.getWindow().getGuiScaledWidth())
                    .setVariable("screenheight", client.getWindow().getGuiScaledHeight())
                    .evaluate();
        } catch (Exception e) {
            System.out.println("Error at SealyHUDElement$getX");
            System.out.println(e.toString());
            return 0; // Fail-safe fallback if math evaluation goes wild
        }
    }

    public int getY() {
        Minecraft client = Minecraft.getInstance();
        if (yExpr == null) return 0;

        try {
            return (int) yExpr
                    .setVariable("screenwidth", client.getWindow().getGuiScaledWidth())
                    .setVariable("screenheight", client.getWindow().getGuiScaledHeight())
                    .evaluate();
        } catch (Exception e) {
            System.out.println("Error at SealyHUDElement$getY");
            System.out.println(e.toString());
            return 0;
        }
    }
    public float getTextSize() {
        Minecraft client = Minecraft.getInstance();
        if (textSizeExpr == null) return 0.5F;

        try {
            return (float) textSizeExpr
                    .setVariable("screenwidth", client.getWindow().getGuiScaledWidth())
                    .setVariable("screenheight", client.getWindow().getGuiScaledHeight())
                    .evaluate();
        } catch (Exception e) {
            return 0.5F;
        }
    }

    public String getText() {
        return SealyHUDTextParser.getJexlText(jexlTextExpr);
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