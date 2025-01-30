package brcomkassin.crates.rewards.animation;

import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.RewardCalculator;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import java.util.HashSet;
import java.util.Set;

public class RewardAnimationHandler {

    private final JavaPlugin plugin;
    private final RewardCalculator rewardCalculator;
    public static final Set<Player> PLAYERS = new HashSet<>();

    public RewardAnimationHandler(JavaPlugin plugin, RewardCalculator rewardCalculator) {
        this.plugin = plugin;
        this.rewardCalculator = rewardCalculator;
    }

    public void initialTask(Player player, Entity crateEntity, Crate crate) {
        Reward reward = rewardCalculator.calculateReward(crate);
        positionThePlayer(player, crateEntity, reward);
    }

    private void circumferenceEffect(Location location, Color color, double particlesPerCircle, float particleSize, double radius, double currentY) {
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

    private void positionThePlayer(Player player, Entity entity, Reward reward) {
        setGameMode(player, GameMode.SPECTATOR);
        Location armorStandLocation = entity.getLocation();
        Vector direction = armorStandLocation.getDirection().multiply(-1);
        Location targetLocation = armorStandLocation.clone().add(direction.multiply(2.2));
        targetLocation.setY(armorStandLocation.getY() + 0.2);

        new BukkitRunnable() {
            @Override
            public void run() {

                Location currentLocation = player.getLocation();
                double distance = currentLocation.distance(targetLocation);

                Location lookAt = armorStandLocation.clone();
                double deltaX = lookAt.getX() - currentLocation.getX();
                double deltaZ = lookAt.getZ() - currentLocation.getZ();
                float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));
                float pitch = currentLocation.getPitch();
                currentLocation.setYaw(yaw);
                currentLocation.setPitch(pitch);
                player.teleport(currentLocation);

                if (distance <= 0.2) {
                    player.teleport(targetLocation);
                    circleAnim(entity, player, reward);
                    PLAYERS.add(player);
                    cancel();
                    return;
                }
                Vector direction = targetLocation.toVector().subtract(currentLocation.toVector()).normalize();
                player.setVelocity(direction.multiply(0.3));
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    private void circleAnim(Entity entity, Player player, Reward reward) {
        new BukkitRunnable() {
            int ticks = 0;
            final double maxTicks = 20 * 1.5;
            double radius = 0.5;

            @Override
            public void run() {
                if (ticks >= maxTicks) {
                    mainAnimation(entity, player, reward, 2.2);
                    cancel();
                } else {
                    radius += 0.05D;
                    circumferenceEffect(entity.getLocation(), Color.BLACK, 30, 1F, radius, 0.1);
                    ticks++;
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private void mainAnimation(Entity entity, Player player, Reward reward, double maxHeight) {
        ModelEngineAnimationManager.play(entity, "1", ModelEngineAnimationManager.AnimationType.OPEN);

        new BukkitRunnable() {
            final Location entityLocation = entity.getLocation().clone();
            double currentY = entityLocation.getY();
            final double finalMaxHeight = currentY + maxHeight;
            double delay = 0;

            @Override
            public void run() {
                if (delay < 15) {
                    delay++;
                    return;
                }

                if (currentY >= finalMaxHeight) {
                    ItemDisplay itemDisplay = setItemDisplay(entityLocation.clone().add(0, maxHeight - 0.5, 0), reward);
                    rotateAndRemoveItemDisplay(itemDisplay, 5, entity);
                    cancel();

                    new BukkitRunnable() {
                        double delay = 0;

                        @Override
                        public void run() {
                            if (delay <= 2) {
                                delay += 1;
                            } else {
                                setGameMode(player, GameMode.SURVIVAL);
                                PLAYERS.remove(player);
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);
                    return;
                }
                circumferenceEffect(entityLocation.clone().add(0, currentY - entityLocation.getY() + 0.3, 0), Color.BLACK, 30, 1f, 0.35D, 0);
                currentY += 0.1;
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private void rotateAndRemoveItemDisplay(ItemDisplay itemDisplay, int seconds, Entity entity) {
        new BukkitRunnable() {
            int ticks = 0;
            final int maxTicks = seconds * 20;
            final float rotationSpeed = 0.08F;

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

    private void setGameMode(Player player, GameMode gameMode) {
        switch (gameMode) {
            case SPECTATOR -> {
                player.setGameMode(GameMode.SPECTATOR);
            }
            case SURVIVAL -> {
                player.setGameMode(GameMode.SURVIVAL);
            }
        }
    }

    private ItemDisplay setItemDisplay(Location location, Reward reward) {
        Location spawn = new Location(location.getWorld(), location.getX(), location.getY() + 0.6, location.getZ());
        ItemDisplay itemDisplay = location.getWorld().spawn(spawn, ItemDisplay.class);
        itemDisplay.setItemStack(reward.getItem());
        return itemDisplay;
    }

}
