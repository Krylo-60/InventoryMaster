package com.krishivstudios.inventorymaster.paper.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class SortCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use /sort.");
            return true;
        }

        if (!player.hasPermission("inventorymaster.use")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to sort inventories.");
            return true;
        }

        // If command is /chestsort, try sorting targeted chest
        if (label.equalsIgnoreCase("chestsort")) {
            Block target = player.getTargetBlockExact(5);
            if (target != null && target.getState() instanceof Container container) {
                sortInventory(container.getInventory(), 0, container.getInventory().getSize());
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
                player.sendMessage(ChatColor.GREEN + "✔ Container sorted successfully!");
                return true;
            } else {
                player.sendMessage(ChatColor.YELLOW + "⚠ Look at a chest or container within 5 blocks to sort it!");
                return true;
            }
        }

        // Default /sort or /invsort: Sort player main inventory (slots 9 to 35)
        sortInventory(player.getInventory(), 9, 27);
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
        player.sendMessage(ChatColor.GREEN + "🎒 Inventory sorted successfully!");
        return true;
    }

    public static void sortInventory(Inventory inv, int start, int count) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ItemStack stack = inv.getItem(start + i);
            if (stack != null && stack.getType() != Material.AIR) {
                items.add(stack.clone());
            } else {
                items.add(new ItemStack(Material.AIR));
            }
        }

        // 1. Combine matching stacks
        for (int i = 0; i < items.size(); i++) {
            ItemStack base = items.get(i);
            if (base.getType() == Material.AIR || base.getAmount() >= base.getMaxStackSize()) continue;

            for (int j = i + 1; j < items.size(); j++) {
                ItemStack other = items.get(j);
                if (base.isSimilar(other)) {
                    int space = base.getMaxStackSize() - base.getAmount();
                    int take = Math.min(space, other.getAmount());
                    base.setAmount(base.getAmount() + take);
                    other.setAmount(other.getAmount() - take);
                    if (other.getAmount() <= 0) {
                        items.set(j, new ItemStack(Material.AIR));
                    }
                    if (base.getAmount() >= base.getMaxStackSize()) break;
                }
            }
        }

        // 2. Sort by category priority & name
        items.sort((a, b) -> {
            if (a.getType() == Material.AIR && b.getType() == Material.AIR) return 0;
            if (a.getType() == Material.AIR) return 1;
            if (b.getType() == Material.AIR) return -1;

            int catA = getCategoryPriority(a.getType());
            int catB = getCategoryPriority(b.getType());

            if (catA != catB) return Integer.compare(catA, catB);

            int nameCmp = a.getType().name().compareToIgnoreCase(b.getType().name());
            if (nameCmp != 0) return nameCmp;

            return Integer.compare(b.getAmount(), a.getAmount());
        });

        // 3. Set back
        for (int i = 0; i < count; i++) {
            ItemStack item = items.get(i);
            inv.setItem(start + i, item.getType() == Material.AIR ? null : item);
        }
    }

    private static int getCategoryPriority(Material mat) {
        String name = mat.name();
        if (name.contains("SWORD") || name.contains("BOW") || name.contains("CROSSBOW") || name.contains("TRIDENT") || name.contains("MACE")) return 0;
        if (name.contains("PICKAXE") || name.contains("AXE") || name.contains("SHOVEL") || name.contains("HOE") || name.contains("SHEARS")) return 1;
        if (name.contains("HELMET") || name.contains("CHESTPLATE") || name.contains("LEGGINGS") || name.contains("BOOTS") || name.contains("ELYTRA") || name.contains("SHIELD")) return 2;
        if (mat.isEdible() || name.contains("APPLE") || name.contains("BREAD") || name.contains("STEAK") || name.contains("POTION")) return 3;
        if (name.contains("DIAMOND") || name.contains("NETHERITE") || name.contains("EMERALD") || name.contains("GOLD") || name.contains("IRON") || name.contains("INGOT") || name.contains("RAW_")) return 4;
        if (name.contains("REDSTONE") || name.contains("REPEATER") || name.contains("COMPARATOR") || name.contains("PISTON") || name.contains("HOPPER") || name.contains("OBSERVER")) return 5;
        if (mat.isBlock()) return 6;
        return 7;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}
