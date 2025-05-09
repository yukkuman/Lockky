package com.dogoma.item;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class ItemRarityChecker {

    private static final ItemRarity MINIMUM_HIGHLIGHT_RARITY = ItemRarity.RARE;


    public static boolean shouldHighlight(ItemStack stack) {
        // if (!stack.) return false;

        MinecraftClient client = MinecraftClient.getInstance();

        List<Text> lore = stack.getTooltip(null, client.player, TooltipType.ADVANCED);
        ItemRarity rarity = ItemRarity.fromLore(lore);
        return rarity.level >= MINIMUM_HIGHLIGHT_RARITY.level;
    }

}
