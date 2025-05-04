package com.dogoma;

import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ClientLockRegistry {
    private static final Map<String, Integer> lockMap = new HashMap<>();

    public static String getItemKey(ItemStack stack) {
        return stack.getItem().toString() + "::" + stack.getName().getString();
    }

    public static int getLockState(ItemStack stack) {
        return lockMap.getOrDefault(getItemKey(stack), 0);
    }

    public static void cycleLockState(ItemStack stack) {
        String key = getItemKey(stack);
        int next = (lockMap.getOrDefault(key, 0) + 1) % 3;
        lockMap.put(key, next);
    }
}
