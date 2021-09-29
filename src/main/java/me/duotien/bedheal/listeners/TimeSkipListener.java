package me.duotien.bedheal.listeners;

import me.duotien.bedheal.BedHeal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

import java.util.List;

public class TimeSkipListener implements Listener {

    @EventHandler
    public void onTimeSkip(TimeSkipEvent event) {
        if (event.getSkipReason().equals(TimeSkipEvent.SkipReason.NIGHT_SKIP) && BedHeal.healOnNightSkipEnabled) {
            List<Player> sleepingPlayers = BedHeal.getSleepingPlayers();
            if (!sleepingPlayers.isEmpty()) {
                for (Player player :
                        sleepingPlayers) {
                    player.setHealth(20.0);
                }
            }
        }
    }
}
