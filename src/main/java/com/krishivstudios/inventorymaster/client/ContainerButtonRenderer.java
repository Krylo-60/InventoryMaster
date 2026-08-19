package com.krishivstudios.inventorymaster.client;

import com.krishivstudios.inventorymaster.feature.QuickStackHandler;
import com.krishivstudios.inventorymaster.sort.InventorySorter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

public final class ContainerButtonRenderer {

    public static void renderButtons(HandledScreen<?> screen, DrawContext context, int mouseX, int mouseY, int x, int y, int backgroundWidth, int backgroundHeight) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        boolean isContainer = screen.getScreenHandler().slots.size() > 36;

        // Button 1: Sort Container / Player Inventory (top right of container header)
        int btnSortX = x + backgroundWidth - 28;
        int btnSortY = y + 6;
        int btnSize = 14;

        boolean hoverSort = mouseX >= btnSortX && mouseX <= btnSortX + btnSize && mouseY >= btnSortY && mouseY <= btnSortY + btnSize;
        int sortBg = hoverSort ? 0x8844AAFF : 0x66222222;
        context.fill(btnSortX, btnSortY, btnSortX + btnSize, btnSortY + btnSize, sortBg);
        context.drawBorder(btnSortX, btnSortY, btnSize, btnSize, hoverSort ? 0xFF00FFFF : 0xFF888888);
        context.drawCenteredTextWithShadow(client.textRenderer, Text.literal("🔄"), btnSortX + 7, btnSortY + 3, 0xFFFFFFFF);

        if (hoverSort) {
            context.drawTooltip(client.textRenderer, Text.literal(isContainer ? "Sort Container" : "Sort Inventory"), mouseX, mouseY);
        }

        // Button 2: Quick Stack (if inside chest/container)
        if (isContainer) {
            int btnStackX = btnSortX - 18;
            int btnStackY = btnSortY;

            boolean hoverStack = mouseX >= btnStackX && mouseX <= btnStackX + btnSize && mouseY >= btnStackY && mouseY <= btnStackY + btnSize;
            int stackBg = hoverStack ? 0x8844FF88 : 0x66222222;
            context.fill(btnStackX, btnStackY, btnStackX + btnSize, btnStackY + btnSize, stackBg);
            context.drawBorder(btnStackX, btnStackY, btnSize, btnSize, hoverStack ? 0xFF44FF88 : 0xFF888888);
            context.drawCenteredTextWithShadow(client.textRenderer, Text.literal("📥"), btnStackX + 7, btnStackY + 3, 0xFFFFFFFF);

            if (hoverStack) {
                context.drawTooltip(client.textRenderer, Text.literal("Quick Stack to Chest"), mouseX, mouseY);
            }
        }
    }

    public static boolean mouseClicked(HandledScreen<?> screen, double mouseX, double mouseY, int button, int x, int y, int backgroundWidth, int backgroundHeight) {
        if (button != 0) return false;

        boolean isContainer = screen.getScreenHandler().slots.size() > 36;
        int btnSortX = x + backgroundWidth - 28;
        int btnSortY = y + 6;
        int btnSize = 14;

        // Click Sort
        if (mouseX >= btnSortX && mouseX <= btnSortX + btnSize && mouseY >= btnSortY && mouseY <= btnSortY + btnSize) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (isContainer) {
                InventorySorter.sortChest(screen.getScreenHandler());
            } else {
                InventorySorter.sortPlayerInventory(screen.getScreenHandler());
            }
            if (client.player != null) {
                client.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.8f, 1.2f);
            }
            return true;
        }

        // Click Quick Stack
        if (isContainer) {
            int btnStackX = btnSortX - 18;
            int btnStackY = btnSortY;

            if (mouseX >= btnStackX && mouseX <= btnStackX + btnSize && mouseY >= btnStackY && mouseY <= btnStackY + btnSize) {
                MinecraftClient client = MinecraftClient.getInstance();
                QuickStackHandler.quickStack(screen.getScreenHandler());
                if (client.player != null) {
                    client.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.8f, 1.0f);
                }
                return true;
            }
        }

        return false;
    }
}
