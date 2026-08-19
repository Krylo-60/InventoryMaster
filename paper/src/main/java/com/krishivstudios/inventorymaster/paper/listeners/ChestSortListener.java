package com.krishivstudios.inventorymaster.paper.listeners;

import com.krishivstudios.inventorymaster.paper.commands.SortCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class ChestSortListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;

        Player player = event.getPlayer();
        if (!player.isSneaking()) return;
        if (player.getInventory().getItemInMainHand().getType() != Material.AIR) return;

        if (event.getClickedBlock().getState() instanceof Container container) {
            if (!player.hasPermission("inventorymaster.use")) return;

            SortCommand.sortInventory(container.getInventory(), 0, container.getInventory().getSize());
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.8f, 1.2f);
            player.sendMessage(ChatColor.GREEN + "✔ Chest sorted!");
            event.setCancelled(true);
        }
    }
}
