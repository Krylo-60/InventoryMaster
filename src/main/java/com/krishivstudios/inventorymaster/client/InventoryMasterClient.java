package com.krishivstudios.inventorymaster.client;

import com.krishivstudios.inventorymaster.feature.AutoRefillHandler;
import com.krishivstudios.inventorymaster.feature.DurabilityWarningHandler;
import com.krishivstudios.inventorymaster.sort.InventorySorter;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Method;

public final class InventoryMasterClient implements ClientModInitializer {

    public static KeyBinding sortKeyBinding;

    @Override
    public void onInitializeClient() {
        try {
            sortKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    "key.inventorymaster.sort",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_R,
                    "category.inventorymaster.general"
            ));

            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                try {
                    AutoRefillHandler.onClientTick();
                    DurabilityWarningHandler.checkDurability();

                    while (sortKeyBinding != null && sortKeyBinding.wasPressed()) {
                        try {
                            if (client.currentScreen != null) {
                                try {
                                    Method getHandler = client.currentScreen.getClass().getMethod("getScreenHandler");
                                    Object handler = getHandler.invoke(client.currentScreen);
                                    if (handler instanceof net.minecraft.screen.ScreenHandler sh) {
                                        InventorySorter.sortChest(sh);
                                        InventorySorter.sortPlayerInventory(sh);
                                    }
                                } catch (Throwable t) {
                                    if (client.player != null) {
                                        InventorySorter.sortPlayerInventory(client.player.playerScreenHandler);
                                    }
                                }
                            } else if (client.player != null) {
                                InventorySorter.sortPlayerInventory(client.player.playerScreenHandler);
                            }
                        } catch (Throwable ignored) {}
                    }
                } catch (Throwable ignored) {}
            });
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
