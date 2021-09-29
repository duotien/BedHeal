package me.duotien.bedheal;

import me.duotien.bedheal.listeners.BedEnterLeaveListener;
import me.duotien.bedheal.listeners.TimeSkipListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class BedHeal extends JavaPlugin {
    public static FileConfiguration config;
    private static BedHeal PLUGIN;
    private static List<Player> sleepingPlayers;

    public static long taskDelay;
    public static long taskPeriod;
    public static int effectDuration;
    public static int effectAmplifier;
    public static boolean healOnNightSkipEnabled;

    @Override
    public void onEnable() {
        PLUGIN = this;
        sleepingPlayers = new ArrayList<>();

        //Config
        this.saveDefaultConfig();
        config = this.getConfig();

        readConFig();

        // Plugin startup logic
        this.getServer().getPluginManager().registerEvents(new BedEnterLeaveListener(), this);
        this.getServer().getPluginManager().registerEvents(new TimeSkipListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("bedheal")) {
            if (!sender.hasPermission("bedheal.reload")) {
                sender.sendMessage(ChatColor.RED + "You cannot run this command");
                return true;
            }
            if (args.length == 0) {
                // /bedheal
                sender.sendMessage(ChatColor.RED + "Usage: /bedheal reload");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("reload-message")));
                this.reloadConfig();
                config = this.getConfig();
                readConFig();
            }
        }

        return false;
    }

    public void readConFig() {
        taskDelay = config.getLong("regenDelayAfterSleepingInTick");
        taskPeriod = config.getLong("regenRateInTick");
        effectDuration = config.getInt("regenDurationInTick");
        effectAmplifier = config.getInt("regenAmplifier");
        healOnNightSkipEnabled = config.getBoolean("fullyHealPlayersOnNightSkip");

        if (effectDuration <= 0) {
            effectDuration = 50;
            Bukkit.getLogger().info(ChatColor.RED + "regenDurationInTick value is invalid, using the default value (50)");
        }
        if (effectAmplifier < 0) {
            effectAmplifier = 0;
            Bukkit.getLogger().info(ChatColor.RED + "regenAmplifier value is invalid, using the default value (0)");
        }
    }

    public static BedHeal getPlugin() {
        return PLUGIN;
    }

    public static List<Player> getSleepingPlayers() {
        return sleepingPlayers;
    }

    public static void addSleepingPlayer(Player player) {
        if (!sleepingPlayers.contains(player)) {
            sleepingPlayers.add(player);
        }
    }

    public static void removeSleepingPlayer(Player player) {
        sleepingPlayers.remove(player);
    }

    public static void removeAllSleepingPlayers() {
        sleepingPlayers.clear();
    }
}
