package io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ContextFunc {

    String path();

    String name();

    String desc();

    String returns();
}