package com.krishivstudios.inventorymaster.sort;

import net.minecraft.item.*;

public final class ItemCategoryClassifier {

    public enum Category {
        WEAPON(0),
        TOOL(1),
        ARMOR(2),
        FOOD(3),
        POTION(4),
        MINERAL(5),
        REDSTONE(6),
        BUILDING(7),
        MISC(8);

        public final int priority;

        Category(int priority) {
            this.priority = priority;
        }
    }

    public static Category classify(ItemStack stack) {
        if (stack.isEmpty()) return Category.MISC;

        Item item = stack.getItem();

        if (item instanceof SwordItem || item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem) {
            return Category.WEAPON;
        }

        if (item instanceof MiningToolItem || item instanceof ShearsItem || item instanceof FishingRodItem || item instanceof FlintAndSteelItem) {
            return Category.TOOL;
        }

        if (item instanceof ArmorItem || item instanceof ElytraItem || item instanceof ShieldItem) {
            return Category.ARMOR;
        }

        if (stack.getComponents().contains(net.minecraft.component.DataComponentTypes.FOOD)) {
            return Category.FOOD;
        }

        if (item instanceof PotionItem || item instanceof SplashPotionItem || item instanceof LingeringPotionItem || item instanceof ExperienceBottleItem) {
            return Category.POTION;
        }

        String id = item.toString().toLowerCase();
        if (id.contains("diamond") || id.contains("netherite") || id.contains("emerald") || id.contains("gold") || id.contains("iron") || id.contains("ingot") || id.contains("raw_")) {
            return Category.MINERAL;
        }

        if (id.contains("redstone") || id.contains("repeater") || id.contains("comparator") || id.contains("piston") || id.contains("hopper") || id.contains("observer")) {
            return Category.REDSTONE;
        }

        if (item instanceof BlockItem) {
            return Category.BUILDING;
        }

        return Category.MISC;
    }
}
