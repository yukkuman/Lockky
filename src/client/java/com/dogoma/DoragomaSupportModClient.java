package com.dogoma;

import com.dogoma.itemLock.ClientLockRegistry;
import com.dogoma.itemLock.LockKeyHandler;
import com.dogoma.partyUI.PartyHudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

public class DoragomaSupportModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LockKeyHandler.register();
		ClientLockRegistry.load();

		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			PartyHudRenderer.render(drawContext, tickDelta);
		});

	}
}