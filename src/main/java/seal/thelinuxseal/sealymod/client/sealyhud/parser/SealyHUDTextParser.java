package seal.thelinuxseal.sealymod.client.sealyhud.parser;

import org.apache.commons.jexl3.*;
import org.apache.commons.jexl3.introspection.JexlPermissions;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.client.ClientContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.player.PlayerContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.system.SystemContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.util.UtilContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.world.WorldContext;
import org.apache.commons.jexl3.JxltEngine.Expression;

public final class SealyHUDTextParser {
    private static final JexlEngine jexl = new JexlBuilder()
            .cache(512)
            .strict(true)
            .silent(false)
            .permissions(JexlPermissions.parse(
                    // 1. Whitelist your custom evaluation wrapper package (wildcard syntax is fine)
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.client.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.player.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.system.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.util.*",
                    "seal.thelinuxseal.sealymod.client.sealyhud.contexts.world.*",

                    // 2. Space-separated syntax for core Java classes (Note the `{}` for each class, NO commas!)
                    "java.lang { String{} Math{} Integer{} Double{} Float{} Long{} }",

                    // 3. Space-separated syntax for Java utility collections
                    "java.util { List{} Map{} Set{} }"
            ))
            .create();
    private static final JxltEngine jxlt = jexl.createJxltEngine();

    private static final ClientContext clientContext = new ClientContext();
    private static final PlayerContext playerContext = new PlayerContext();
    private static final SystemContext systemContext = new SystemContext();
    private static final UtilContext utilContext = new UtilContext();
    private static final WorldContext worldContext = new WorldContext();
    private static final JexlContext context = new MapContext();

    public static void init(){
        context.set("client",clientContext);
        context.set("player",playerContext);
        context.set("system",systemContext);
        context.set("util",utilContext);
        context.set("world",worldContext);
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
            // Return an empty literal expression so it safely renders nothing
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
