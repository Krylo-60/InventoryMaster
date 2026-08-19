package com.krishivstudios.inventorymaster.feature;

import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

public class ChestSearchFilter {
    private static String searchQuery = "";
    private static boolean active = false;

    public static String getSearchQuery() {
        return searchQuery;
    }

    public static void setSearchQuery(String query) {
        searchQuery = query != null ? query.trim().toLowerCase() : "";
        active = !searchQuery.isEmpty();
    }

    public static boolean isActive() {
        return active;
    }

    public static void clear() {
        searchQuery = "";
        active = false;
    }

    public static boolean matches(ItemStack stack) {
        if (!active || searchQuery.isEmpty() || stack.isEmpty()) return true;

        String name = stack.getName().getString().toLowerCase();
        String id = Registries.ITEM.getId(stack.getItem()).toString().toLowerCase();

        return name.contains(searchQuery) || id.contains(searchQuery);
    }
}
