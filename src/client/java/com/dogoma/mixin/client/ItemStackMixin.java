package com.dogoma.mixin.client;

import com.dogoma.ClientLockRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "getTooltip", at = @At("RETURN"), cancellable = true)
    private void addLockTooltip(Item.TooltipContext context, PlayerEntity player, TooltipType type, CallbackInfoReturnable<List<Text>> cir) {
        ItemStack self = (ItemStack)(Object)this;
        int state = ClientLockRegistry.getLockState(self);
        switch (state) {
            case 1 -> cir.getReturnValue().add(Text.literal("§6[ロック中]"));
            case 2 -> cir.getReturnValue().add(Text.literal("§c[スタン中]"));
        }
    }
}
