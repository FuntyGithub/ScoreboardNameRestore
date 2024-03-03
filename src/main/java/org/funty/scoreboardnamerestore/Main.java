package org.funty.scoreboardnamerestore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.funty.scoreboardnamerestore.listeners.JoinListener;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("[ScoreboardNameRestore] Enabled!");

        //listener
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
