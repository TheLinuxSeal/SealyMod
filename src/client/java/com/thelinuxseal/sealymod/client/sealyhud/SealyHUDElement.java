package com.thelinuxseal.sealymod.client.sealyhud;

import com.thelinuxseal.sealymod.client.sealyhud.parser.SealyHUDJexlTextParser;
import com.thelinuxseal.sealymod.client.sealyhud.parser.SealyHUDSimpleTextParser;
import net.minecraft.client.Minecraft;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class SealyHUDElement {
    private String xFormula;
    private String yFormula;
    private String textTemplate;
    private String textSizeFormula;
    private boolean enabled;
    private boolean advancedParseMode;


    private transient Expression xExpr;
    private transient Expression yExpr;
    private transient Expression textSizeExpr;

    // Default constructor for serialization/initial default values
    public SealyHUDElement(String xFormula, String yFormula, String textSizeFormula, String textTemplate, boolean advancedParseMode, boolean enabled) {
        compile(xFormula, yFormula, textSizeFormula, textTemplate, advancedParseMode, enabled);
    }

    public void compile(String xFormula, String yFormula, String textSizeFormula, String textTemplate, boolean advancedParseMode, boolean enabled) {
        this.xFormula = xFormula;
        this.yFormula = yFormula;
        this.textSizeFormula = textSizeFormula;
        this.textTemplate = textTemplate;
        this.advancedParseMode = advancedParseMode;
        this.enabled = enabled;

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
    public int getTextSize() {
        Minecraft client = Minecraft.getInstance();
        if (textSizeExpr == null) return 12;

        try {
            return (int) textSizeExpr
                    .setVariable("screenwidth", client.getWindow().getGuiScaledWidth())
                    .setVariable("screenheight", client.getWindow().getGuiScaledHeight())
                    .evaluate();
        } catch (Exception e) {
            return 12;
        }
    }



    public String getText() {
        if (advancedParseMode) {
            return SealyHUDJexlTextParser.getJexlText(textTemplate);
        } else {
            return SealyHUDSimpleTextParser.getSimpleText(textTemplate);
        }
    }

    // Getters and Setters needed for serialization and YACL bindings
    public String getXFormula() { return xFormula; }
    public void setXFormula(String formula) { compile(formula, this.yFormula, this.textSizeFormula, this.textTemplate, this.advancedParseMode, this.enabled); }

    public String getYFormula() { return yFormula; }
    public void setYFormula(String formula) { compile(this.xFormula, formula, this.textSizeFormula, this.textTemplate, this.advancedParseMode, this.enabled); }

    public String getTextSizeFormula() {return textSizeFormula;}
    public void setTextSizeFormula(String formula) {compile(this.xFormula,this.yFormula,formula,this.textTemplate,this.advancedParseMode,this.enabled);}

    public String getTextTemplate() { return textTemplate; }
    public void setTextTemplate(String text) { compile(this.xFormula, this.yFormula, this.textSizeFormula, text, this.advancedParseMode, this.enabled); }

    public boolean getAdvancedParseMode() {return advancedParseMode;}
    public void setAdvancedParseMode(boolean mode) {compile(this.xFormula,this.yFormula,this.textSizeFormula,this.textTemplate,mode,this.enabled);}

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}