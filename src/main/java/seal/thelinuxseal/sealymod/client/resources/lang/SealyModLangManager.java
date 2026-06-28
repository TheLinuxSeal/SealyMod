package seal.thelinuxseal.sealymod.client.resources.lang;

public final class SealyModLangManager {
    public static final SealyModLang MAIN = new SealyModLang("main.json");
    public static final SealyModLang SEALYHUD_DOCS = new SealyModLang("sealyhud_docs.json");

    public static void reload(){
        MAIN.reload();
        SEALYHUD_DOCS.reload();
    }

    private SealyModLangManager() {}
}