package seal.thelinuxseal.sealymod.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import seal.thelinuxseal.sealymod.client.resources.lang.LangInstance;
import seal.thelinuxseal.sealymod.client.sealyhud.SealyHud;
import net.fabricmc.api.ClientModInitializer;
import seal.thelinuxseal.sealymod.client.startup.StartupTask;

public class SealyModClient implements ClientModInitializer {
	public static final String MOD_ID = "sealymod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static SealyHud sealyhud = new SealyHud();
	public static LangInstance lang = new LangInstance(lang -> lang+".json");

	@Override
	public void onInitializeClient() {
		StartupTask.run();
	}
}