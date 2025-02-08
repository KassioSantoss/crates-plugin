package brcomkassin.crates.rewards.animation.utils;

import brcomkassin.CratesPlugin;
import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.utils.ColoredLogger;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

public class AnimationUtilsManager {

    private static final CratesPlugin plugin = CratesPlugin.getInstance();
    public static final Set<String> PLAYERS_IN_SPECTATOR_MODE = new HashSet<>();

    public static void generateCircularPattern(Location location, Color color, double particlesPerCircle, float particleSize, double radius, double currentY) {
        Particle.DustOptions dustOptions = new Particle.DustOptions(color, particleSize);
        double y = location.getY() + currentY;

        for (int i = 0; i < particlesPerCircle; i++) {
            double angle = 2 * Math.PI * i / particlesPerCircle;

            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);

            Location particleLocation = new Location(location.getWorld(), x, y, z);

            location.getWorld().spawnParticle(Particle.REDSTONE, particleLocation, 1, dustOptions);
        }
    }

    public static void positionThePlayer(Player player, Entity entity, Runnable onFinish) {
        setGameMode(player, GameMode.SPECTATOR);
        Location armorStandLocation = entity.getLocation();
        Vector direction = armorStandLocation.getDirection().multiply(-1);
        Location targetLocation = armorStandLocation.clone().add(direction.multiply(2.2));
        targetLocation.setY(armorStandLocation.getY() + 0.2);

        new BukkitRunnable() {

            @Override
            public void run() {
                final Location currentLocation = player.getLocation();
                final double distance = currentLocation.distance(targetLocation);
                final Location lookAt = armorStandLocation.clone();
                final double deltaX = lookAt.getX() - currentLocation.getX();
                final double deltaZ = lookAt.getZ() - currentLocation.getZ();
                final float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));
                final float pitch = currentLocation.getPitch();

                currentLocation.setYaw(yaw);
                currentLocation.setPitch(pitch);
                player.teleport(currentLocation);

                if (distance <= 0.2) {
                    player.teleport(targetLocation);
                    cancel();
                    if (onFinish != null) {
                        onFinish.run();
                    }
                    return;
                }
                Vector direction = targetLocation.toVector().subtract(currentLocation.toVector()).normalize();
                player.setVelocity(direction.multiply(0.3));
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    public static void rotateAndRemoveItemDisplay(ItemDisplay itemDisplay, int seconds, Entity entity) {
        new BukkitRunnable() {
            int ticks = 0;
            final int maxTicks = seconds * 20;
            final float rotationSpeed = 0.06F;

            @Override
            public void run() {
                if (ticks >= maxTicks) {
                    itemDisplay.remove();
                    entity.remove();
                    cancel();
                    return;
                }
                Transformation transformation = itemDisplay.getTransformation();
                transformation.getLeftRotation().rotateY(rotationSpeed);
                itemDisplay.setTransformation(transformation);
                ticks++;
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public static void moveItemDisplayUpAndDown(ItemDisplay itemDisplay, double maxHeight, double speed, int durationInSeconds) {
        new BukkitRunnable() {
            final double originalY = itemDisplay.getLocation().getY();
            boolean goingUp = true;
            int ticks = 0;
            final int maxTicks = durationInSeconds * 20;

            @Override
            public void run() {
                Location location = itemDisplay.getLocation();
                double newY = location.getY() + (goingUp ? speed : -speed);

                if (goingUp && newY >= originalY + maxHeight) {
                    goingUp = false;
                } else if (!goingUp && newY <= originalY) {
                    goingUp = true;
                }

                location.setY(newY);
                itemDisplay.teleport(location);
                ticks++;
                if (ticks >= maxTicks) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public static void moveItemDisplay(ItemDisplay itemDisplay, double maxHeight, double speed, boolean moveUp) {
        new BukkitRunnable() {
            final double originalY = itemDisplay.getLocation().getY();
            final double targetY = moveUp ? originalY + maxHeight : originalY - maxHeight;

            @Override
            public void run() {
                Location location = itemDisplay.getLocation();
                double newY = location.getY() + (moveUp ? speed : -speed);

                if ((moveUp && newY >= targetY) || (!moveUp && newY <= targetY)) {
                    location.setY(targetY);
                    itemDisplay.teleport(location);
                    cancel();
                    return;
                }

                location.setY(newY);
                itemDisplay.teleport(location);
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    public static void setGameMode(Player player, GameMode gameMode) {
        switch (gameMode) {
            case SPECTATOR -> {
                player.setGameMode(GameMode.SPECTATOR);
            }
            case SURVIVAL -> {
                player.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

    public static ItemDisplay setItemDisplay(Location location, Reward reward) {
        Location spawn = new Location(location.getWorld(), location.getX(), location.getY() + 0.5, location.getZ());
        ItemDisplay itemDisplay = location.getWorld().spawn(spawn, ItemDisplay.class);
        Transformation transformation = itemDisplay.getTransformation();
        transformation.getScale().set(0.7D, 0.7D, 0.7D);
        itemDisplay.setTransformation(transformation);
        itemDisplay.setItemStack(reward.getItem());
        return itemDisplay;
    }

    public static void startRunnable(long delay, Runnable task) {
        new BukkitRunnable() {
            @Override
            public void run() {
                task.run();
            }
        }.runTaskLater(plugin, delay);
    }

}
