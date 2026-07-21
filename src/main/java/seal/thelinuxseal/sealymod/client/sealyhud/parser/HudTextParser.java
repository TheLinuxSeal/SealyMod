package seal.thelinuxseal.sealymod.client.sealyhud.parser;

import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.introspection.JexlPermissions;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.MainContext;
import org.apache.commons.jexl3.JxltEngine.Expression;

public class HudTextParser {
    private static final JexlEngine jexl = new JexlBuilder()
            .cache(512)
            .strict(true)
            .silent(false)
            .permissions(JexlPermissions.parse(
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.client.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.player.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.system.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.common.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.util.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.world.*",
                    "java.lang { String{} Math{} Integer{} Double{} Float{} Long{} }",
                    "java.util { List{} Map{} Set{} }"
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
            return "Error "+e.toString();
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
