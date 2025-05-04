package com.dogoma;

import com.dogoma.mixin.client.HandledScreenAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.glfw.GLFW;

public class LockKeyHandler {
    private static final int KEY = GLFW.GLFW_KEY_L;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), KEY)) {
                if (client.currentScreen instanceof HandledScreen<?> screen) {
                    double mouseX = client.mouse.getX() * (double) client.getWindow().getScaledWidth() / client.getWindow().getWidth();
                    double mouseY = client.mouse.getY() * (double) client.getWindow().getScaledHeight() / client.getWindow().getHeight();
                    Slot slot = ((HandledScreenAccessor) screen).callGetSlotAt(mouseX, mouseY);
                    if (slot != null && slot.hasStack()) {
                        ClientLockRegistry.cycleLockState(slot.getStack());
                    }
                }
                break;
            }
        });
    }
}
