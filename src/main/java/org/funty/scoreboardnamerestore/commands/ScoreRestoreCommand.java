package org.funty.scoreboardnamerestore.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.funty.scoreboardnamerestore.listeners.JoinListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScoreRestoreCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length < 3) {
            commandSender.sendMessage("§c[ScoreboardNameRestore] Not enough arguments!");
            return false;
        }
        if (commandSender.hasPermission("scoreboardnamerestore.scorerestore")) {
            new JoinListener().restoreScore(args[0], args[1], args[2].equalsIgnoreCase("replace"));
            commandSender.sendMessage("§a[ScoreboardNameRestore] scoreboard data from '"+args[0]+"' moved to '"+args[1]+"'! Data was "+(args[2].equalsIgnoreCase("replace") ? "replaced" : "added")+".");
        } else commandSender.sendMessage("§c[ScoreboardNameRestore] You don't have permissions for this command!");

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        if (!commandSender.hasPermission("scoreboardnamerestore.scorerestore")) return Collections.emptyList();

        String[] commands;
        List<String> commandsList = new ArrayList<>();

        if (args.length == 1) {
            commandsList.addAll(Bukkit.getScoreboardManager().getMainScoreboard().getEntries());
        } else if (args.length == 2) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                commandsList.add(player.getName());
            }
        }

        if (args.length == 3) commands = new String[]{"replace", "add"};
        else commands = commandsList.toArray(new String[0]);

        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList(commands), completions);
        Collections.sort(completions);
        return completions;
    }
}
