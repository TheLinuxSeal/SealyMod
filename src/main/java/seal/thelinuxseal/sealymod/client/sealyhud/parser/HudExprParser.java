package seal.thelinuxseal.sealymod.client.sealyhud.parser;

import net.minecraft.client.Minecraft;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class HudExprParser {
    private static  Minecraft client(){return Minecraft.getInstance();}
    public static Expression build(String expr){
        return new ExpressionBuilder(expr)
                .variables("screenwidth", "screenheight")
                .build();
    }

    public static Expression build(String expr, String def){
        try {
            return build(expr);
        } catch (Exception e) {
            return new ExpressionBuilder(def).build();
        }
    }

    public static double eval(Expression expr){
        return expr.setVariable("screenwidth", client().getWindow().getGuiScaledWidth())
                .setVariable("screenheight", client().getWindow().getGuiScaledHeight())
                .evaluate();
    }

    public static double eval(Expression expr, double def){
        try {
            return eval(expr);
        } catch (Exception e) {
            return def;
        }
    }
}
