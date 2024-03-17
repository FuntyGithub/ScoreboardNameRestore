package org.funty.scoreboardnamerestore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.*;

public class PlayerInfoCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("scoreboardnamerestore.playerinfo.use")) {
            commandSender.sendMessage("§c[ScoreboardNameRestore] You don't have permissions for this command!");
            return false;
        }

        OfflinePlayer player;
        if (args.length > 0) player = Bukkit.getOfflinePlayer(args[0]);
        else player = Bukkit.getOfflinePlayer(commandSender.getName());

        commandSender.sendMessage(ChatColor.GREEN +"v----[PlayerInfo]----v");
        commandSender.sendMessage(ChatColor.GREEN +"Name: "+ChatColor.GRAY+player.getName());
        if (commandSender.hasPermission("scoreboardnamerestore.playerinfo.uuid")) commandSender.sendMessage(ChatColor.GREEN +"UUID: "+ChatColor.GRAY+player.getUniqueId());
        if (commandSender.hasPermission("scoreboardnamerestore.playerinfo.firstplayed")) commandSender.sendMessage(ChatColor.GREEN +"First played: "+ChatColor.GRAY+(player.getFirstPlayed() == 0 ? "-":new Date(player.getFirstPlayed())));
        if (commandSender.hasPermission("scoreboardnamerestore.playerinfo.lastplayed")) commandSender.sendMessage(ChatColor.GREEN +"Last played: "+ChatColor.GRAY+ (player.getLastPlayed() == 0 ? "-": (player.isOnline() ? "currently online":new Date(player.getLastPlayed()))));
        if (commandSender.hasPermission("scoreboardnamerestore.playerinfo.banned")) commandSender.sendMessage(ChatColor.GREEN +"Banned: "+ChatColor.GRAY+player.isBanned());
        commandSender.sendMessage(ChatColor.GREEN +"^----[PlayerInfo]----^");

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("scoreboardnamerestore.playerinfo.use")) return null;

        List<String> commandsList = new ArrayList<>();

        if (args.length == 1) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                commandsList.add(player.getName());
            }
        } else return new ArrayList<>();

        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[args.length - 1], Arrays.asList(commandsList.toArray(new String[0])), completions);
        Collections.sort(completions);
        return completions;
    }
}
