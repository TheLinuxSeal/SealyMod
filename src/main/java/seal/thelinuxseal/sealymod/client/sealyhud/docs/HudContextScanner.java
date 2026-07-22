package seal.thelinuxseal.sealymod.client.sealyhud.docs;

import com.google.gson.JsonObject;
import seal.thelinuxseal.sealymod.client.SealyModClient;
import seal.thelinuxseal.sealymod.client.sealyhud.editor.help.HudHelpScreen;
import seal.thelinuxseal.sealymod.client.sealyhud.editor.help.HudHelpScreenNode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class HudContextScanner {
    static JsonObject docs;
    private HudContextScanner(){}
    public static HudHelpScreenNode scan(String rootName, Object root, HudHelpScreen helpScreen, String name) {
        docs = SealyModClient.lang.getAsJsonElement("sealymod.sealyhud.editor.help.docs").getAsJsonObject();
        HudHelpScreenNode node = new HudHelpScreenNode(root,helpScreen, new ClassRecord(name));
        scan(rootName, root, new HashSet<>(), helpScreen, node);
        return node;
    }

    private static void scan(String path, Object object, Set<Object> visited, HudHelpScreen helpScreen, HudHelpScreenNode node) {
        if (object == null) {
            return;
        }

        // Prevent infinite recursion if two contexts reference each other
        if (!visited.add(object)) {
            return;
        }

        Class<?> clazz = object.getClass();

        // Scan functions
        for (Method method : clazz.getDeclaredMethods()) {

            ContextFunc function = method.getAnnotation(ContextFunc.class);

            if (function == null) {
                continue;
            }

            String name;
            String desc;
            String returns;

            if (docs.has(function.path())) {
                JsonObject doc = docs.getAsJsonObject(function.path());
                if (doc.has("name")) {
                    name = doc.get("name").getAsString();
                } else {
                    name = function.name();
                }
                if (doc.has("desc")) {
                    desc = doc.get("desc").getAsString();
                } else {
                    desc = function.desc();
                }
                if (doc.has("returns")) {
                    returns = doc.get("returns").getAsString();
                } else {
                    returns = function.returns();
                }
            } else {
                name = function.name();
                desc = function.desc();
                returns = function.returns();
            }

            node.addFunc(new FuncRecord(name,desc,path,returns));

            /*helpScreen.entries.add(Map.of(
                    "name", name,
                    "desc", desc,
                    "path", function.path(),
                    "returns", returns
            ));*/


        }

        // Scan child contexts
        for (Field field : clazz.getDeclaredFields()) {


            if (!field.isAnnotationPresent(ContextClass.class)) {
                continue;
            }

            if (!field.trySetAccessible()) {
                continue;
            }

            try {
                Object child = field.get(object);
                HudHelpScreenNode node2 = new HudHelpScreenNode(child,helpScreen,new ClassRecord(field.getAnnotation(ContextClass.class).name()));

                node.addChild(node2);

                if (child != null) {
                    scan(path + "." + field.getName(), child, visited, helpScreen, node2);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    public record FuncRecord(String name, String desc, String path, String returns){};
    public record ClassRecord(String name){};
}
