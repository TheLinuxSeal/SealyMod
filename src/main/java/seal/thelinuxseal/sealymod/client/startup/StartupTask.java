package seal.thelinuxseal.sealymod.client.startup;

import seal.thelinuxseal.sealymod.client.SealyModClient;
import seal.thelinuxseal.sealymod.client.config.ConfigHandler;
import seal.thelinuxseal.sealymod.client.resources.ResourceReloadListener;

public class StartupTask {
    public static void run(){
        ConfigHandler.load();
        ResourceReloadListener.init();
        SealyModClient.sealyhud.init();
    }
}
