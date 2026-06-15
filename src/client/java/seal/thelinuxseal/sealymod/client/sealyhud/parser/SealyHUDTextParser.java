package seal.thelinuxseal.sealymod.client.sealyhud.parser;

import seal.thelinuxseal.sealymod.client.sealyhud.contexts.client.ClientContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.player.PlayerContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.system.SystemContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.util.UtilContext;
import seal.thelinuxseal.sealymod.client.sealyhud.contexts.world.WorldContext;
import org.apache.commons.jexl3.JxltEngine.Expression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.JexlContext;

public final class SealyHUDTextParser {
    public static ClientContext clientContext = new ClientContext();
    public static PlayerContext playerContext = new PlayerContext();
    public static SystemContext systemContext = new SystemContext();
    public static UtilContext utilContext = new UtilContext();
    public static WorldContext worldContext = new WorldContext();
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
            return "Error";
        }
    }
}
