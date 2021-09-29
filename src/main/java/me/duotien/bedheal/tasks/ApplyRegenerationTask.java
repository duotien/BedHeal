package me.duotien.bedheal.tasks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ApplyRegenerationTask extends BukkitRunnable {

    //add keyword `final`
    private final LivingEntity player;
    private final int effectDuration;
    private final int effectAmplifier;

    public ApplyRegenerationTask(LivingEntity player, int effectDuration, int effectAmplifier) {
        this.effectDuration = effectDuration;
        this.effectAmplifier = effectAmplifier;
        this.player = player;
    }

    @Override
    public void run() {
        if (player.isSleeping()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, effectDuration, effectAmplifier));
        } else {
            this.cancel();
        }
    }
}
