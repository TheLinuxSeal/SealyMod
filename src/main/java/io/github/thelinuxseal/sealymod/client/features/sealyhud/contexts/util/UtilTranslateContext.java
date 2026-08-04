package io.github.thelinuxseal.sealymod.client.features.sealyhud.contexts.util;

import io.github.thelinuxseal.sealymod.client.SealyModClient;
import net.minecraft.client.resources.language.I18n;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.editor.docs.ContextFunc;

public final class UtilTranslateContext {
    @ContextFunc(path = "util.translate.mojang(String key)", name = "Mojang Translation", desc = "Translates a key into its value, using Mojang's translation system.", returns = "String")
    public String mojang(String key){
        return I18n.get(key);
    }
    @ContextFunc(path = "util.translate.sealyMod(String key)", name = "SealyMod Translation", desc = "Translates a key into its value, using SealyMod's translation system.", returns = "String")
    public String sealyMod(String key){
        return SealyModClient.lang.get(key);
    }
}
