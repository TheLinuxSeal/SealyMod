package seal.thelinuxseal.sealymod.client.sealyhud.contexts.util;

import net.minecraft.client.resources.language.I18n;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;

public final class UtilTranslateContext {
    public String i18n(String key){
        return I18n.get(key);
    }
    public String sealyLang(String key){
        return SealyModLangManager.MAIN.get(key);
    }
}
