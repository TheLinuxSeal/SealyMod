package seal.thelinuxseal.sealymod.client.sealyhud.contexts.util;

import net.minecraft.client.resources.language.I18n;
import seal.thelinuxseal.sealymod.client.resources.lang.SealyModLangManager;

public final class UtilContext {
    public String translate(String key){
        return I18n.get(key);
    }
    public String sealyModTranslate(String key){
        return SealyModLangManager.MAIN.get(key);
    }
}
