package com.krishivstudios.inventorymaster.paper;

import com.krishivstudios.inventorymaster.paper.commands.SortCommand;
import com.krishivstudios.inventorymaster.paper.listeners.ChestSortListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class InventoryMasterPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (getCommand("sort") != null) {
            SortCommand cmd = new SortCommand();
            getCommand("sort").setExecutor(cmd);
            getCommand("sort").setTabCompleter(cmd);
        }

        getServer().getPluginManager().registerEvents(new ChestSortListener(), this);

        getLogger().info("=========================================");
        getLogger().info(" 🎒 InventoryMaster Paper Plugin v1.0.0");
        getLogger().info(" Author: Krylo_plays (Krishiv Studios)");
        getLogger().info(" Paper, Purpur, Spigot & Folia Ready!");
        getLogger().info("=========================================");
    }

    @Override
    public void onDisable() {
        getLogger().info("InventoryMaster plugin disabled.");
    }
}
