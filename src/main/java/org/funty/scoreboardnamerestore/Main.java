package org.funty.scoreboardnamerestore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.funty.scoreboardnamerestore.commands.PlayerInfoCommand;
import org.funty.scoreboardnamerestore.commands.ScoreRestoreCommand;
import org.funty.scoreboardnamerestore.listeners.JoinListener;

public final class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Bukkit.getLogger().info("[ScoreboardNameRestore] Enabled!");
        saveDefaultConfig();

        // listener
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new JoinListener(), this);

        // commands
        getCommand("ScoreRestore").setExecutor(new ScoreRestoreCommand());
        getCommand("ScoreRestore").setTabCompleter(new ScoreRestoreCommand());
        getCommand("PlayerInfo").setExecutor(new PlayerInfoCommand());
        getCommand("PlayerInfo").setTabCompleter(new PlayerInfoCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getPlugin(){
        return plugin;
    }

}
