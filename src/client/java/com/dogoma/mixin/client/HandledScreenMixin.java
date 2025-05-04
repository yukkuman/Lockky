package com.dogoma.mixin.client;

import com.dogoma.ClientLockRegistry;
import com.dogoma.LockKeyHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class HandledScreenMixin {

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void onKeyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (LockKeyHandler.LOCK_KEY.matchesKey(keyCode, scanCode)) {
            HandledScreen<?> screen = (HandledScreen<?>) (Object)this;

            MinecraftClient client = MinecraftClient.getInstance();
            double mouseX = client.mouse.getX() * (double) client.getWindow().getScaledWidth() / client.getWindow().getWidth();
            double mouseY = client.mouse.getY() * (double) client.getWindow().getScaledHeight() / client.getWindow().getHeight();

            Slot slot = ((HandledScreenAccessor) screen).callGetSlotAt(mouseX, mouseY);
            if (slot != null && slot.hasStack()) {
                ItemStack stack = slot.getStack();
                ClientLockRegistry.cycleLockState(stack);
                cir.setReturnValue(true); // 入力を消費
            }
        }

        if (keyCode == MinecraftClient.getInstance().options.dropKey.getDefaultKey().getCode()) {
            HandledScreen<?> screen = (HandledScreen<?>) (Object)this;

            MinecraftClient client = MinecraftClient.getInstance();
            double mouseX = client.mouse.getX() * (double) client.getWindow().getScaledWidth() / client.getWindow().getWidth();
            double mouseY = client.mouse.getY() * (double) client.getWindow().getScaledHeight() / client.getWindow().getHeight();

            Slot slot = ((HandledScreenAccessor) screen).callGetSlotAt(mouseX, mouseY);
            if (slot != null && slot.hasStack()) {
                ItemStack stack = slot.getStack();
                int state = ClientLockRegistry.getLockState(stack);
                if (state > 0) {
                    cir.setReturnValue(true); // ドロップキーによるドロップをキャンセル
                }
            }
        }
    }

}
