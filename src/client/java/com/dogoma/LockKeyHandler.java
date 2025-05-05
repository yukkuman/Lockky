package com.dogoma;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;


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
    }
}
