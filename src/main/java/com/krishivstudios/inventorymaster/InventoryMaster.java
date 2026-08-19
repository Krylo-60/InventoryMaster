package com.krishivstudios.inventorymaster;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class InventoryMaster implements ModInitializer {
    public static final String MOD_ID = "inventorymaster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("InventoryMaster initialized successfully! (Ultimate Inventory QoL)");
    }
}
