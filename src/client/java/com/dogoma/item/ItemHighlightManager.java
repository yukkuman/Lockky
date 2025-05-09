package com.dogoma.item;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static com.dogoma.item.ItemRarityChecker.shouldHighlight;

public class ItemHighlightManager {

    private static final Map<Integer, Integer> highlightTicks = new HashMap<>();

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ItemHighlightManager::onTick);
    }

    private static void onTick(MinecraftClient client) {
        if (client.world == null) return;

        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof ItemEntity itemEntity) {
                ItemStack stack = itemEntity.getStack();
                if (!highlightTicks.containsKey(itemEntity.getId()) && shouldHighlight(stack)) {
                    highlightTicks.put(itemEntity.getId(), 60);
                    ItemRarity rarity = ItemRarity.fromLore(stack.getTooltip(null, client.player, TooltipType.ADVANCED));
                    client.player.sendMessage(
                            Text.literal("[" + rarity.keyword + "ドロップ] ")
                                    .formatted(Formatting.GOLD)
                                    .append(stack.getName()),
                            false
                    );
                    entity.setGlowing(true);
                    entity.getScoreboardTeam().setColor(rarity.color);
                }
            }
        }

        highlightTicks.replaceAll((id, ticks) -> ticks - 1);
        highlightTicks.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }

    /*
    private void onRender(WorldRenderContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.cameraEntity == null) return;

        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof ItemEntity item && highlightTicks.containsKey(item.getId())) {
                ItemStack stack = item.getStack();
                ItemRarity rarity = ItemRarity.fromLore(stack.getTooltip(null, null));
                Color color = rarity.color;

                Vec3d pos = item.getPos().subtract(client.cameraEntity.getPos());

                RenderSystem.enableBlend();
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                RenderSystem.disableTexture();

                BufferBuilder buffer = BufferRenderer.getImmediate().getBuffer();
                MatrixStack matrices = context.matrixStack();

                matrices.push();
                matrices.translate(pos.x, pos.y + 0.3, pos.z);
                Matrix4f model = matrices.peek().getPositionMatrix();

                buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
                buffer.vertex(model, -0.2f, 0, -0.2f).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
                buffer.vertex(model,  0.2f, 0, -0.2f).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
                buffer.vertex(model,  0.2f, 0,  0.2f).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
                buffer.vertex(model, -0.2f, 0,  0.2f).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).next();
                BufferRenderer.drawWithShader(buffer.end());

                matrices.pop();

                RenderSystem.enableTexture();
                RenderSystem.disableBlend();
            }
        }
    }
    */

}
