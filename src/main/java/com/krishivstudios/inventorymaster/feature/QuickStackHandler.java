package com.krishivstudios.inventorymaster.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.util.HashSet;
import java.util.Set;

public final class QuickStackHandler {

    public static void quickStack(ScreenHandler handler) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) return;

        int containerSlotCount = handler.slots.size() - 36;
        if (containerSlotCount <= 0) return;

        // 1. Collect all items currently in the container
        Set<String> containerItems = new HashSet<>();
        for (int i = 0; i < containerSlotCount; i++) {
            ItemStack stack = handler.getSlot(i).getStack();
            if (!stack.isEmpty()) {
                containerItems.add(stack.getItem().toString());
            }
        }

        if (containerItems.isEmpty()) return;

        // 2. Transfer matching items from player inventory (slots containerSlotCount to handler.slots.size() - 1)
        int playerStart = containerSlotCount;
        int playerEnd = handler.slots.size();

        for (int p = playerStart; p < playerEnd; p++) {
            Slot playerSlot = handler.getSlot(p);
            ItemStack playerStack = playerSlot.getStack();

            if (!playerStack.isEmpty() && containerItems.contains(playerStack.getItem().toString())) {
                // Quick transfer shift-click to move to container
                client.interactionManager.clickSlot(handler.syncId, p, 0, SlotActionType.QUICK_MOVE, client.player);
            }
        }
    }
}
