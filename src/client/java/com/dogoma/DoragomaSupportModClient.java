package com.dogoma;

import net.fabricmc.api.ClientModInitializer;

public class DoragomaSupportModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LockKeyHandler.register();
		ClientLockRegistry.load();
	}
}