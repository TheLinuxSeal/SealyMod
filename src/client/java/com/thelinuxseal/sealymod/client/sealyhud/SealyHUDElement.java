package com.thelinuxseal.sealymod.client.sealyhud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.network.chat.Component;

public class SealyHUDElement {
    private String xFormula;
    private String yFormula;
    private String textTemplate;
    private boolean enabled;

    private transient Expression xExpr;
    private transient Expression yExpr;

    // Default constructor for serialization/initial default values
    public SealyHUDElement(String xFormula, String yFormula, String textTemplate, boolean enabled) {
        compile(xFormula, yFormula, textTemplate, enabled);
    }

    public void compile(String xFormula, String yFormula, String textTemplate, boolean enabled) {
        this.xFormula = xFormula;
        this.yFormula = yFormula;
        this.textTemplate = textTemplate;
        this.enabled = enabled;

        try {
            this.xExpr = new ExpressionBuilder(xFormula)
                    .variables("screenwidth", "screenheight")
                    .build();

            this.yExpr = new ExpressionBuilder(yFormula)
                    .variables("screenwidth", "screenheight")
                    .build();
        } catch (Exception e) {
            // Fallback safe expressions in case a player types a syntax error in the config menu
            this.xExpr = new ExpressionBuilder("10").build();
            this.yExpr = new ExpressionBuilder("10").build();
        }
    }

    public int getX() {
        Minecraft client = Minecraft.getInstance();
        if (xExpr == null) return 10;

        try {
            return (int) xExpr
                    .setVariable("screenwidth", client.getWindow().getGuiScaledWidth())
                    .setVariable("screenheight", client.getWindow().getGuiScaledHeight())
                    .evaluate();
        } catch (Exception e) {
            return 10; // Fail-safe fallback if math evaluation goes wild
        }
    }

    public int getY() {
        Minecraft client = Minecraft.getInstance();
        if (yExpr == null) return 10;

        try {
            return (int) yExpr
                    .setVariable("screenwidth", client.getWindow().getGuiScaledWidth())
                    .setVariable("screenheight", client.getWindow().getGuiScaledHeight())
                    .evaluate();
        } catch (Exception e) {
            return 10;
        }
    }



    public String getText() {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;

        String fps = String.valueOf(SealyHUDVars.getFPS());
        String x = SealyHUDVars.getX();
        String y = SealyHUDVars.getY();
        String z = SealyHUDVars.getZ();
        String dim = SealyHUDVars.getDim();
        String biome = SealyHUDVars.getBiome();
        String gameMode = SealyHUDVars.getGameMode();
        String ping = SealyHUDVars.getPing();
        String dir = SealyHUDVars.getDir();
        //client.level.getServer().



        // Regex pattern: Matches "%%", "%fps", "%x", "%y", or "%z"
        Pattern pattern = Pattern.compile("%%|%fps|%x|%y|%z|%dim|%dimension|%biome|%gamemode|%ping|%dir|%direction|%facing");
        Matcher matcher = pattern.matcher(textTemplate);
        StringBuilder sb = new StringBuilder();

        // Scan through the string from left to right
        while (matcher.find()) {
            String token = matcher.group();

            switch (token) {
                case "%%" -> matcher.appendReplacement(sb, Matcher.quoteReplacement("%"));
                case "%fps" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(fps));
                case "%x" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(x));
                case "%y" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(y));
                case "%z" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(z));
                case "%dim" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dim));
                case "%dimension" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dim));
                case "%biome" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(biome));
                case "%gamemode" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(gameMode));
                case "%ping" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(ping));
                case "%dir" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dir));
                case "%direction" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dir));
                case "%facing" -> matcher.appendReplacement(sb, Matcher.quoteReplacement(dir));
                default -> matcher.appendReplacement(sb, Matcher.quoteReplacement(token));
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    // Getters and Setters needed for serialization and YACL bindings
    public String getXFormula() { return xFormula; }
    public void setXFormula(String formula) { compile(formula, this.yFormula, this.textTemplate, this.enabled); }

    public String getYFormula() { return yFormula; }
    public void setYFormula(String formula) { compile(this.xFormula, formula, this.textTemplate, this.enabled); }

    public String getTextTemplate() { return textTemplate; }
    public void setTextTemplate(String text) { compile(this.xFormula, this.yFormula, text, this.enabled); }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}