package seal.thelinuxseal.sealymod.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seal.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import seal.thelinuxseal.sealymod.client.resources.SealyModResourceReloadListenerInitializer;
import seal.thelinuxseal.sealymod.client.sealyhud.SealyHUD;
import net.fabricmc.api.ClientModInitializer;

public class SealyModClient implements ClientModInitializer {
	public static final String MOD_ID = "sealymod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private SealyHUD sealhud;

	@Override
	public void onInitializeClient() {

		SealyModConfigHandler.load();
		SealyModResourceReloadListenerInitializer.init();
		sealhud = new SealyHUD();
		sealhud.init();

	}
	public static void reloadAfterReconfig(){}
}