package com.dogoma.mixin.client;

import com.dogoma.ClientLockRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(method = "dropSelectedItem", at = @At("HEAD"), cancellable = true)
    private void onDropSelectedItem(boolean entireStack, CallbackInfoReturnable<Boolean> cir) {
        PlayerInventory inv = (PlayerInventory) (Object) this;
        ItemStack stack = inv.player.currentScreenHandler.getCursorStack();

        if (ClientLockRegistry.getLockState(stack) > 0) {
            cir.setReturnValue(false); // ドロップさせない
        }
    }
}
