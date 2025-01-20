package brcomkassin.crates.rewards.animation;

import brcomkassin.CratesPlugin;
import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.RewardCalculator;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;

public class RewardHandlerAnimation {

    private final CrateCache crateCache;
    private final RewardCalculator rewardCalculator;

    public RewardHandlerAnimation(CrateCache crateCache, RewardCalculator rewardCalculator) {
        this.crateCache = crateCache;
        this.rewardCalculator = rewardCalculator;
    }

    public void animReward(Player player, Entity crateEntity, Crate crate) {
        Reward reward = rewardCalculator.calculateReward(crate);
        spawnParticleCircle(reward, player, crateEntity.getLocation(), 2.2D);
    }

    private void spawnParticleCircle(Reward reward, Player player, Location center, final double maxHeight) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.BLACK, 0.8F);
        double finalMaxHeight = center.getY() + maxHeight;
        Location location = new Location(center.getWorld(), center.getX(), finalMaxHeight - 0.5, center.getZ());

        BukkitTask bukkitTask = new BukkitRunnable() {
            double currentY = center.getY() + 0.3;
            int delay = 0;
            final int particlesPerCircle = 30;
            final double radius = 0.2;
            final double yIncrement = 0.1;

            @Override
            public void run() {
                if (currentY >= finalMaxHeight) {
                    ItemDisplay itemDisplay = setItemDisplay(location, reward);
                    rotateAndRemoveItemDisplay(itemDisplay, 5);
                    player.getInventory().addItem(reward.getItem());
                    cancel();
                    return;
                }

                if (delay <= 15) {
                    delay = delay + 1;
                    return;
                }

                for (int i = 0; i < particlesPerCircle; i++) {
                    double angle = 2 * Math.PI * i / particlesPerCircle;

                    double x = center.getX() + radius * Math.cos(angle);
                    double z = center.getZ() + radius * Math.sin(angle);

                    Location particleLocation = new Location(center.getWorld(), x, currentY, z);

                    player.spawnParticle(Particle.REDSTONE, particleLocation, 1, dustOptions);
                }

                currentY += yIncrement;
            }
        }.runTaskTimer(CratesPlugin.getInstance(), 0, 1);
    }

    private void rotateAndRemoveItemDisplay(ItemDisplay itemDisplay, int seconds) {
        new BukkitRunnable() {
            int ticks = 0;
            final int maxTicks = seconds * 20;
            final float rotationSpeed = 0.08F;

            @Override
            public void run() {
                if (ticks >= maxTicks) {
                    itemDisplay.remove();
                    cancel();
                    return;
                }

                Transformation transformation = itemDisplay.getTransformation();
                transformation.getLeftRotation().rotateY(rotationSpeed);
                itemDisplay.setTransformation(transformation);

                ticks++;
            }
        }.runTaskTimer(CratesPlugin.getInstance(), 0, 1);
    }

    private ItemDisplay setItemDisplay(Location location, Reward reward) {
        Location spawn = new Location(location.getWorld(), location.getX(), location.getY() + 0.6, location.getZ());
        ItemDisplay itemDisplay = location.getWorld().spawn(spawn, ItemDisplay.class);
        itemDisplay.setItemStack(reward.getItem());
        return itemDisplay;
    }

}