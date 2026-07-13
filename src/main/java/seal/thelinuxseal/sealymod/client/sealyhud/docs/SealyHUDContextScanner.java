package seal.thelinuxseal.sealymod.client.sealyhud.docs;

import seal.thelinuxseal.sealymod.client.sealyhud.editor.SealyHUDEditorHelpScreen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class SealyHUDContextScanner {
    private  SealyHUDContextScanner(){}
    public static void scan(String rootName, Object root, SealyHUDEditorHelpScreen helpScreen) {
        scan(rootName, root, new HashSet<>(), helpScreen);
    }

    private static void scan(String path, Object object, Set<Object> visited, SealyHUDEditorHelpScreen helpScreen) {
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

            SealyHUDFuncAnnotation function = method.getAnnotation(SealyHUDFuncAnnotation.class);

            if (function == null) {
                continue;
            }

            helpScreen.entries.add(new SealyHUDEditorHelpScreen.HelpEntry(
                    function.name(),
                    function.desc(),
                    function.path()
            ));

            //function.


        }

        // Scan child contexts
        for (Field field : clazz.getDeclaredFields()) {

            if (!field.isAnnotationPresent(SealyHUDSubAnnotation.class)) {
                continue;
            }

            field.setAccessible(true);

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
