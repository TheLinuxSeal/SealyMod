package io.github.thelinuxseal.sealymod.client;

import io.github.thelinuxseal.sealymod.client.commands.sealymod.SealyModCommand;
import io.github.thelinuxseal.sealymod.client.config.ConfigHandler;
import io.github.thelinuxseal.sealymod.client.resources.ResourceReloadListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.thelinuxseal.sealymod.client.resources.lang.LangInstance;
import io.github.thelinuxseal.sealymod.client.sealyhud.SealyHud;
import net.fabricmc.api.ClientModInitializer;

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
		ConfigHandler.load();
		ResourceReloadListener.init();
		SealyModClient.sealyhud.init();
		SealyModCommand.init();
	}
}