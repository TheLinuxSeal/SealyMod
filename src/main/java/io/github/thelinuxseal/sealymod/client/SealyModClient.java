package io.github.thelinuxseal.sealymod.client;

import io.github.thelinuxseal.sealymod.client.sealylang.LangInstance;
import io.github.thelinuxseal.sealymod.client.sealylang.SealyLang;
import io.github.thelinuxseal.sealymod.common.ModFeatureRegistry;
import io.github.thelinuxseal.sealymod.client.commands.SealyModCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.thelinuxseal.sealymod.client.features.sealyhud.SealyHud;
import net.fabricmc.api.ClientModInitializer;

public class SealyModClient implements ClientModInitializer {
	public static final String MOD_ID = "sealymodclient";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ModFeatureRegistry clientRegistry = new ModFeatureRegistry();
	public static final SealyHud sealyhud = clientRegistry.register(new SealyHud());
	public static final SealyLang sealylang = new SealyLang();
	public static LangInstance lang = new LangInstance(lang -> lang+".json");


	@Override
	public void onInitializeClient() {
		sealylang.init();
		clientRegistry.initialize();
		SealyModCommand.init();
	}
}