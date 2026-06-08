package com.thelinuxseal.sealymod.client.sealyhud.parser;

import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.JxltEngine.Expression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.JexlContext;

public final class SealyHUDJexlTextParser {
    private static final JexlEngine jexl = new JexlBuilder().cache(512).strict(true).silent(true).create();
    // Create the template engine from the base JEXL engine
    private static final JxltEngine jxlt = jexl.createJxltEngine();
    private static final JexlContext context = new MapContext();

    public static String getJexlText(String textTemplate) {
        // Use createExpression from JxltEngine to handle text templates
        Expression expr = jxlt.createExpression(textTemplate);
        context.set("fps", SealyHUDVars.getFPS());
        context.set("x", SealyHUDVars.getX());
        context.set("y", SealyHUDVars.getY());
        context.set("z", SealyHUDVars.getZ());
        context.set("dim", SealyHUDVars.getDim());
        context.set("dimension", SealyHUDVars.getDim());
        context.set("biome", SealyHUDVars.getBiome());
        context.set("dir", SealyHUDVars.getDir());
        context.set("direction", SealyHUDVars.getDir());
        context.set("facing", SealyHUDVars.getDir());
        context.set("ping", SealyHUDVars.getPing());


        Object result = expr.evaluate(context);
        return result != null ? result.toString() : "";
    }
}
