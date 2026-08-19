package com.krishivstudios.inventorymaster.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public final class AutoRefillHandler {

    private static Item lastMainHandItem = Items.AIR;

    public static void onClientTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) return;

        PlayerInventory inv = client.player.getInventory();
        ItemStack currentMain = inv.getMainHandStack();
        ItemStack currentOff = inv.getStack(40); // Offhand slot

        // 1. Auto-Totem Restock
        if (currentOff.isEmpty()) {
            for (int i = 9; i < 36; i++) {
                ItemStack stack = inv.getStack(i);
                if (stack.isOf(Items.TOTEM_OF_UNDYING)) {
                    client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, i, 40, SlotActionType.SWAP, client.player);
                    break;
                }
            }
        }

        // 2. Auto-Refill Placed Block or Broken Tool
        if (currentMain.isEmpty() && lastMainHandItem != Items.AIR) {
            int selectedSlot = inv.selectedSlot;
            for (int i = 9; i < 36; i++) {
                ItemStack stack = inv.getStack(i);
                if (!stack.isEmpty() && stack.isOf(lastMainHandItem)) {
                    client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, i, selectedSlot, SlotActionType.SWAP, client.player);
                    break;
                }
            }
        }

        lastMainHandItem = currentMain.isEmpty() ? Items.AIR : currentMain.getItem();
    }
}
