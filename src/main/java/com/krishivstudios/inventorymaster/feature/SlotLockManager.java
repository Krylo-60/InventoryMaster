package com.krishivstudios.inventorymaster.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages player inventory slot locking (Alt + Click).
 * Locked slots are excluded from sorting and quick-stack dumping.
 */
public class SlotLockManager {
    private static final Set<Integer> lockedSlots = new HashSet<>();

    public static boolean isLocked(int slotIndex) {
        return lockedSlots.contains(slotIndex);
    }

    public static void toggleLock(int slotIndex) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (lockedSlots.contains(slotIndex)) {
            lockedSlots.remove(slotIndex);
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§6[InventoryMaster] §7Slot #" + slotIndex + " §aUnlocked!"), true);
            }
        } else {
            lockedSlots.add(slotIndex);
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§6[InventoryMaster] §7Slot #" + slotIndex + " §6🔒 Locked (Protected from sorting)"), true);
            }
        }
    }

    public static void clearAllLocks() {
        lockedSlots.clear();
    }

    public static Set<Integer> getLockedSlots() {
        return lockedSlots;
    }
}
