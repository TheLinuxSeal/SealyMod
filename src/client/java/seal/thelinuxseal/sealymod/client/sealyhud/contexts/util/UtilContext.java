package seal.thelinuxseal.sealymod.client.sealyhud.contexts.util;

import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLang;
import net.minecraft.client.resources.language.I18n;

public final class UtilContext {
    public String translate(String key){
        return I18n.get(key);
    }
    public String sealyTranslate(String key){
        return SealyModLang.get(key);
    }
}
