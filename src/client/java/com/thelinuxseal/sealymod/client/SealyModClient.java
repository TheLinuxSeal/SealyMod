package com.thelinuxseal.sealymod.client;

import com.thelinuxseal.sealymod.client.config.SealyModConfigHandler;
import com.thelinuxseal.sealymod.client.sealyhud.SealyHUD;
import net.fabricmc.api.ClientModInitializer;

public class SealyModClient implements ClientModInitializer {
	private SealyHUD sealhud;

	@Override
	public void onInitializeClient() {

		SealyModConfigHandler.load();
		sealhud = new SealyHUD();
		sealhud.init();

	}
	public static void reloadAfterReconfig(){}
}