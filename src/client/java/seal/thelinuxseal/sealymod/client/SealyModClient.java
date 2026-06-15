package seal.thelinuxseal.sealymod.client;

import seal.thelinuxseal.sealymod.client.commands.CommandInitializer;
import seal.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import seal.thelinuxseal.sealymod.client.resources.SealyModResourceReloadListenerInitializer;
import seal.thelinuxseal.sealymod.client.sealyhud.SealyHUD;
import net.fabricmc.api.ClientModInitializer;

public class SealyModClient implements ClientModInitializer {
	private SealyHUD sealhud;

	@Override
	public void onInitializeClient() {

		SealyModConfigHandler.load();
		SealyModResourceReloadListenerInitializer.init();
		CommandInitializer.init();
		sealhud = new SealyHUD();
		sealhud.init();

	}
	public static void reloadAfterReconfig(){}
}