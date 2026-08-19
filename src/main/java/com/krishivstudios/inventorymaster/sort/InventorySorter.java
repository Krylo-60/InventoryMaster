package com.krishivstudios.inventorymaster.sort;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.util.*;

public final class InventorySorter {

    public static void sortContainer(ScreenHandler handler, int startIndex, int slotCount) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) return;

        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < slotCount; i++) {
            Slot slot = handler.getSlot(startIndex + i);
            items.add(slot.getStack().copy());
        }

        // 1. Merge stacks
        for (int i = 0; i < items.size(); i++) {
            ItemStack base = items.get(i);
            if (base.isEmpty() || base.getCount() >= base.getMaxCount()) continue;

            for (int j = i + 1; j < items.size(); j++) {
                ItemStack other = items.get(j);
                if (ItemStack.areItemsAndComponentsEqual(base, other)) {
                    int space = base.getMaxCount() - base.getCount();
                    int take = Math.min(space, other.getCount());
                    base.increment(take);
                    other.decrement(take);
                    if (other.isEmpty()) {
                        items.set(j, ItemStack.EMPTY);
                    }
                    if (base.getCount() >= base.getMaxCount()) break;
                }
            }
        }

        // 2. Sort items
        items.sort((a, b) -> {
            if (a.isEmpty() && b.isEmpty()) return 0;
            if (a.isEmpty()) return 1;
            if (b.isEmpty()) return -1;

            ItemCategoryClassifier.Category catA = ItemCategoryClassifier.classify(a);
            ItemCategoryClassifier.Category catB = ItemCategoryClassifier.classify(b);

            if (catA != catB) {
                return Integer.compare(catA.priority, catB.priority);
            }

            int nameCmp = a.getName().getString().compareToIgnoreCase(b.getName().getString());
            if (nameCmp != 0) return nameCmp;

            return Integer.compare(b.getCount(), a.getCount());
        });

        // Apply back to slots
        int syncId = handler.syncId;
        for (int i = 0; i < slotCount; i++) {
            Slot slot = handler.getSlot(startIndex + i);
            ItemStack target = items.get(i);
            if (!ItemStack.areItemsAndComponentsEqual(slot.getStack(), target) || slot.getStack().getCount() != target.getCount()) {
                slot.setStack(target);
            }
        }
    }

    public static void sortPlayerInventory(ScreenHandler handler) {
        // Player main inventory slots are 9 to 35 (27 slots)
        int start = 9;
        int count = 27;
        if (handler.slots.size() >= start + count) {
            sortContainer(handler, start, count);
        }
    }

    public static void sortChest(ScreenHandler handler) {
        // Container slots are from 0 to slots.size() - 36
        int containerSlots = handler.slots.size() - 36;
        if (containerSlots > 0) {
            sortContainer(handler, 0, containerSlots);
        }
    }
}
