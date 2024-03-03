package org.funty.scoreboardnamerestore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class JoinListener implements Listener {
    @EventHandler
    public void onInteract(PlayerJoinEvent event){
        if (event.getJoinMessage() != null && event.getJoinMessage().contains("formerly known as")) {
            // Name has changed

            // get Names
            String oldName = event.getJoinMessage().replaceAll(".*\\(formerly known as (.+?)\\).*", "$1");

            // get scoreboard
            Scoreboard scoreboard = event.getPlayer().getScoreboard();

            // add the scores to the new player
            for (Score score : scoreboard.getScores(oldName)) {
                score.getObjective().getScore(event.getPlayer()).setScore(score.getScore());
            }

            // reset old player
            scoreboard.resetScores(oldName);

            Bukkit.getLogger().info("[ScoreboardNameRestore] "+event.getPlayer().getName() + "'s scoreboard data restored!");
        }
    }
}
