package io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs;

import com.google.gson.JsonObject;
import io.github.thelinuxseal.sealymod.client.SealyModClient;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class HudDocScanner {
    static JsonObject docs;
    private HudDocScanner(){}
    public static HudDocScreenNode scan(String rootName, Object root, HudDocScreen helpScreen, String name) {
        docs = SealyModClient.lang.getAsJsonElement("sealymod.sealyhud.editor.docs.overrides").getAsJsonObject();
        HudDocScreenNode node = new HudDocScreenNode(root,helpScreen, new ClassRecord(name));
        scan(rootName, root, new HashSet<>(), helpScreen, node);
        return node;
    }

    private static void scan(String path, Object object, Set<Object> visited, HudDocScreen helpScreen, HudDocScreenNode node) {
        if (object == null) {
            return;
        }

        // Prevent infinite recursion if two contexts reference each other
        if (!visited.add(object)) {
            return;
        }

        Class<?> clazz = object.getClass();

        Method[] methods = clazz.getDeclaredMethods();
        Arrays.sort(methods, Comparator.comparing(method -> {
            ContextFunc func = method.getAnnotation(ContextFunc.class);
            return func != null ? func.name() : "";
        }));

        // Scan functions
        for (Method method : methods) {

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

            node.addFunc(new FuncRecord(name,desc,function.path(),returns));

            /*helpScreen.entries.add(Map.of(
                    "name", name,
                    "desc", desc,
                    "path", function.path(),
                    "returns", returns
            ));*/


        }

        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(field -> {
            ContextClass context = field.getAnnotation(ContextClass.class);
            return context != null ? context.name() : "";
        }));

        // Scan child contexts
        for (Field field : fields) {


            if (!field.isAnnotationPresent(ContextClass.class)) {
                continue;
            }

            if (!field.trySetAccessible()) {
                continue;
            }

            try {
                Object child = field.get(object);
                HudDocScreenNode node2 = new HudDocScreenNode(child,helpScreen,new ClassRecord(field.getAnnotation(ContextClass.class).name()));

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
