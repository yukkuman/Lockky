package com.dogoma.mixin.client;

import com.dogoma.ClientLockRegistry;
import com.dogoma.LockKeyHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public class HandledScreenMixin extends Screen {

    public HandledScreenMixin(Text title) {
        super(title);
    }

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


    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        HandledScreen<?> screen = (HandledScreen<?>) (Object) this;

        for (Slot slot : screen.getScreenHandler().slots) {
            int slotId = slot.id;
            ItemStack item = screen.getScreenHandler().getSlot(slotId).getStack();
            int state = ClientLockRegistry.getLockState(item);
            if (state == 0) continue;

            int x = slot.x + screen.width;
            int y = slot.y + screen.height;

            int color = switch (state) {
                case 1 -> 0x80FFFF00; // 半透明黄色
                case 2 -> 0x80FF0000;       // 半透明赤
                default -> 0;
            };

            context.fill(x, y, x + 16, y + 16, color);

        }
    }


}
