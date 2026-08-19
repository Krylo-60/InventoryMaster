package com.krishivstudios.inventorymaster.client;

import com.krishivstudios.inventorymaster.feature.AutoRefillHandler;
import com.krishivstudios.inventorymaster.feature.DurabilityWarningHandler;
import com.krishivstudios.inventorymaster.sort.InventorySorter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class InventoryMasterClient implements ClientModInitializer {

    public static KeyBinding sortKeyBinding;

    @Override
    public void onInitializeClient() {
        sortKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.inventorymaster.sort",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.inventorymaster.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            AutoRefillHandler.onClientTick();
            DurabilityWarningHandler.checkDurability();

            while (sortKeyBinding.wasPressed()) {
                if (client.currentScreen instanceof HandledScreen<?> handled) {
                    InventorySorter.sortChest(handled.getScreenHandler());
                    InventorySorter.sortPlayerInventory(handled.getScreenHandler());
                } else if (client.player != null) {
                    InventorySorter.sortPlayerInventory(client.player.playerScreenHandler);
                }
            }
        });
    }
}
