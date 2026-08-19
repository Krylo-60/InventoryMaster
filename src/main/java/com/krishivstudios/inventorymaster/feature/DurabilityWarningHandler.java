package com.krishivstudios.inventorymaster.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public final class DurabilityWarningHandler {

    private static long lastAlertTime = 0;
    private static String alertMessage = null;
    private static int alertAlpha = 0;

    public static void checkDurability() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        long now = System.currentTimeMillis();
        if (now - lastAlertTime < 4000) return; // Alert once every 4 seconds max

        checkItem(client.player.getMainHandStack(), "Main Hand Tool");
        checkItem(client.player.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST), "Chestplate / Elytra");
    }

    private static void checkItem(ItemStack stack, String slotName) {
        if (stack.isEmpty() || !stack.isDamageable()) return;

        int max = stack.getMaxDamage();
        int current = stack.getDamage();
        int remaining = max - current;
        float percent = ((float) remaining / max) * 100.0f;

        if (percent <= 10.0f || remaining <= 15) {
            MinecraftClient client = MinecraftClient.getInstance();
            lastAlertTime = System.currentTimeMillis();
            alertMessage = "⚠️ WARNING: " + stack.getName().getString() + " durability critical (" + remaining + "/" + max + ")!";
            alertAlpha = 255;

            if (client.player != null) {
                client.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_PLING.value(), 1.0f, 0.5f);
            }
        }
    }

    public static void renderHUDWarning(DrawContext context) {
        if (alertMessage == null || alertAlpha <= 0) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        int x = width / 2;
        int y = height - 68;

        int color = (alertAlpha << 24) | 0xFF3333; // Red warning
        context.drawCenteredTextWithShadow(client.textRenderer, Text.literal(alertMessage), x, y, color);

        alertAlpha -= 2; // Fade out slowly
        if (alertAlpha <= 0) {
            alertMessage = null;
        }
    }
}
