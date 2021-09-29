package me.duotien.bedheal.listeners;

import me.duotien.bedheal.BedHeal;
import me.duotien.bedheal.tasks.ApplyRegenerationTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.scheduler.BukkitTask;

public class BedEnterLeaveListener implements Listener {

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        // player successfully enter the bed
        if (event.getBedEnterResult().equals(PlayerBedEnterEvent.BedEnterResult.OK)) {
            BedHeal.addSleepingPlayer(player);
            BukkitTask applyRegenerationTask = new ApplyRegenerationTask(player, BedHeal.effectDuration, BedHeal.effectAmplifier).runTaskTimer(BedHeal.getPlugin(), BedHeal.taskDelay, BedHeal.taskPeriod);
        }
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        // Remove the player from sleepingPlayers
        BedHeal.removeSleepingPlayer(player);

    }
}
