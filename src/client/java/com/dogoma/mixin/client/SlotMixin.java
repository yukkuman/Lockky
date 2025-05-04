package com.dogoma.mixin.client;

import com.dogoma.ClientLockRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMixin {
    @Inject(method = "canTakeItems", at = @At("HEAD"), cancellable = true)
    private void blockTakeIfStunned(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = ((Slot)(Object)this).getStack();
        if (ClientLockRegistry.getLockState(stack) == 2) {
            cir.setReturnValue(false);
        }
    }
}
