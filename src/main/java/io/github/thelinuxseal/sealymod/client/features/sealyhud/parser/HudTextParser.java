package io.github.thelinuxseal.sealymod.client.features.sealyhud.parser;

import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.introspection.JexlPermissions;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.MainContext;
import org.apache.commons.jexl3.JxltEngine.Expression;

public class HudTextParser {
    private static final JexlEngine jexl = new JexlBuilder()
            .cache(512)
            .strict(true)
            .silent(false)
            .permissions(JexlPermissions.parse(
                    "io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.*",
                    "io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.client.*",
                    "io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.player.*",
                    "io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.system.*",
                    "io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects.*",
                    "io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.util.*",
                    "io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.world.*",
                    "java.lang { String{} Math{} Integer{} Double{} Float{} Long{} }",
                    "java.util { List{} Map{} Set{} }",
                    "io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.objects { Block { set(); } Chunk { set(); } Entity { set(); } Position { set(); } Rotation { set(); } Item { set(); } ItemContainer { set(); } }"
            ))
            .create();
    private static final JxltEngine jxlt = jexl.createJxltEngine();

    private static final JexlContext context = new MapContext();

    public static void init(){
        context.set("client",MainContext.self.client);
        context.set("player",MainContext.self.player);
        context.set("system",MainContext.self.system);
        context.set("util",MainContext.self.util);
        context.set("world",MainContext.self.world);


    }


    public static String getJexlText(Expression expr) {
        try {
            Object result = expr.evaluate(context);
            return result != null ? result.toString() : "";
        } catch (Exception e) {
            return "[Eval Error]";
        }
    }

    public static Expression createExpr(String expr) {
        if (expr == null || expr.trim().isEmpty()) {
            return jxlt.createExpression("");
        }
        try {
            return jxlt.createExpression(expr);
        } catch (Exception e) {
            // Fallback expression that visually warns the user they have a syntax error
            return jxlt.createExpression("[Syntax Error]");
        }
    }
}
