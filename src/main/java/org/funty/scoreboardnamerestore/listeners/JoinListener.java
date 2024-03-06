package org.funty.scoreboardnamerestore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (!scoreboard.getScores(player.getUniqueId().toString()).isEmpty()) restoreScore(player.getUniqueId().toString(), player.getName(), false);

        // check if name was used by someone else before
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (Objects.equals(offlinePlayer.getName(), player.getName()) && offlinePlayer.getUniqueId() != player.getUniqueId()) {
                // there is another player offline that has the same last known name
                restoreScore(offlinePlayer.getName(), offlinePlayer.getUniqueId().toString(), false);
                // TODO: TESTEN:
                // TODO:          - testen ob loop außerhalb der if ein denkfehler hat
                // TODO:          - testen ob replace false funktioniert
                // TODO:          - Testen ob problem hier unten jetzt funktioiniert
//  TODO:
//                    Wenn spieler name changed oder das erste mal joint, dann schauen ob es zu dem Namen schon scores gibt, falls ja, temporary moven zu uuid des alten besitzers.
//                    Für den fall, dass jemand: Name ändert, wer anders den namen nimmt und joint, bevor der alte account wieder gekommen ist
//  TODO:
//                    Command oldname -> gibt es jemanden mit dem Namen? -nein-> War es mal sein Name? (HUMAN verification) -ja-> sync


                return;
            }
        }

        if (event.getJoinMessage() != null && event.getJoinMessage().contains("formerly known as")) {
            // Name has changed

            // get Names
            String oldName = event.getJoinMessage().replaceAll(".*\\(formerly known as (.+?)\\).*", "$1");

            // restore scores
            restoreScore(oldName, player.getName(),true);
        }
    }

    public void restoreScore(String oldName, String newName, Boolean replace) {

        // get scoreboard
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        // add the scores to the new player
        for (Score score : scoreboard.getScores(oldName)) {
            if (replace) {
                score.getObjective().getScore(newName).setScore(score.getScore());
            } else {
                score.getObjective().getScore(newName).setScore(score.getObjective().getScore(newName).getScore() + score.getScore());
            }
        }

        // reset old player
        scoreboard.resetScores(oldName);

        // log to console
        Bukkit.getLogger().info("[ScoreboardNameRestore] scoreboard data from '"+oldName+"' moved to '"+newName+"'!");
    }
}
