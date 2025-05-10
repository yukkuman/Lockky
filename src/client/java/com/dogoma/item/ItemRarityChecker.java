package com.dogoma.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemRarityChecker {

    private static final ItemRarity MINIMUM_HIGHLIGHT_RARITY = ItemRarity.RARE;


    public static boolean shouldHighlight(ItemStack stack) {
        // if (!stack.) return false;

        MinecraftClient client = MinecraftClient.getInstance();
        Item.TooltipContext context = new Item.TooltipContext() {
            @Override
            public @Nullable RegistryWrapper.WrapperLookup getRegistryLookup() {
                return null;
            }

            @Override
            public float getUpdateTickRate() {
                return 0;
            }

            @Override
            public @Nullable MapState getMapState(MapIdComponent mapIdComponent) {
                return null;
            }
        };

        List<Text> lore = stack.getTooltip(context, client.player, TooltipType.ADVANCED);
        ItemRarity rarity = ItemRarity.fromLore(lore);
        return rarity.level >= MINIMUM_HIGHLIGHT_RARITY.level;
    }

}
