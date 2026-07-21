package seal.thelinuxseal.sealymod.client.sealyhud.docs;

import com.google.gson.JsonObject;
import seal.thelinuxseal.sealymod.client.SealyModClient;
import seal.thelinuxseal.sealymod.client.sealyhud.editor.HudHelpScreen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HudContextScanner {
    static JsonObject docs;
    private HudContextScanner(){}
    public static void scan(String rootName, Object root, HudHelpScreen helpScreen) {
        docs = SealyModClient.lang.getAsJsonElement("sealymod.sealyhud.editor.help.docs").getAsJsonObject();
        scan(rootName, root, new HashSet<>(), helpScreen);
    }

    private static void scan(String path, Object object, Set<Object> visited, HudHelpScreen helpScreen) {
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

            helpScreen.entries.add(Map.of(
                    "name", name,
                    "desc", desc,
                    "path", function.path(),
                    "returns", returns
            ));


        }

        // Scan child contexts
        for (Field field : clazz.getDeclaredFields()) {

            if (field.isAnnotationPresent(ContextIgnore.class)) {
                continue;
            }

            if (!field.trySetAccessible()) {
                continue;
            }

            try {
                Object child = field.get(object);

                if (child != null) {
                    scan(path + "." + field.getName(), child, visited, helpScreen);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
