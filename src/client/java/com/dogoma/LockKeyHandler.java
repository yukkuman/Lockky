package com.dogoma;

import com.dogoma.mixin.client.HandledScreenAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;
import java.util.logging.Logger;

public class LockKeyHandler {

    public static final KeyBinding LOCK_KEY = KeyBindingHelper.registerKeyBinding(
            new KeyBinding(
                    "key.clientitemlock.lock",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_L,
                    "category.clientitemlock"
            )
    );

    public static void register() {
        /*ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (Drop_key.wasPressed()) {
                ItemStack item = MinecraftClient.getInstance().player.getInventory().getSelectedStack();
                if (ClientLockRegistry.getLockState(item)>0) {
                    client.
                }
            }
        });*/
    }
}
