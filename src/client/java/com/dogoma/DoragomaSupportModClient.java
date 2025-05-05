package com.dogoma;

import com.dogoma.itemLock.ClientLockRegistry;
import com.dogoma.itemLock.LockKeyHandler;
import net.fabricmc.api.ClientModInitializer;

public class DoragomaSupportModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LockKeyHandler.register();
		ClientLockRegistry.load();
	}
}