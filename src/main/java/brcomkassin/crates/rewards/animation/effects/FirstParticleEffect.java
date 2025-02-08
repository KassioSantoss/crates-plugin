package brcomkassin.crates.rewards.animation.effects;

import brcomkassin.CratesPlugin;
import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.RewardCalculator;
import brcomkassin.crates.rewards.animation.utils.AnimationUtilsManager;
import brcomkassin.crates.rewards.animation.utils.ModelEngineAnimationManager;
import brcomkassin.crates.rewards.animation.service.AnimationService;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FirstParticleEffect implements AnimationService {

    private final CratesPlugin plugin = CratesPlugin.getInstance();
    private final RewardCalculator rewardCalculator;

    public FirstParticleEffect(RewardCalculator rewardCalculator) {
        this.rewardCalculator = rewardCalculator;
    }

    @Override
    public void run(Player player, Entity crateEntity, Crate crate) {
        Reward reward = rewardCalculator.calculateReward(crate);

        AnimationUtilsManager.positionThePlayer(player, crateEntity, () -> {
            circleAnim(crateEntity, player, reward, crate);
            AnimationUtilsManager.PLAYERS_IN_SPECTATOR_MODE.add(player.getName());
        });
    }

    private void circleAnim(Entity entity, Player player, Reward reward, Crate crate) {
        new BukkitRunnable() {
            int ticks = 0;
            final double maxTicks = 20 * 1.2;
            double radius = 0.5;
            final Location lookAt = entity.getLocation().clone();
            final Location currentLocation = player.getLocation();
            final float pitch = entity.getLocation().getPitch() + 15;
            final double deltaX = lookAt.getX() - currentLocation.getX();
            final double deltaZ = lookAt.getZ() - currentLocation.getZ();
            final float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));

            @Override
            public void run() {
                if (ticks >= maxTicks) {
                    mainAnimation(entity, player, reward, crate, 2.2);
                    cancel();
                } else {
                    currentLocation.setYaw(yaw);
                    currentLocation.setPitch(pitch);
                    player.teleport(currentLocation);
                    radius += 0.05D;
                    AnimationUtilsManager.generateCircularPattern(entity.getLocation(), crate.getAnimationProperties().getColor(), 30, 1F, radius, 0.1);
                    ticks++;
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private void mainAnimation(Entity entity, Player player, Reward reward, Crate crate, double maxHeight) {
        ModelEngineAnimationManager.play(entity, crate);

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
                    ItemDisplay itemDisplay = AnimationUtilsManager.setItemDisplay(entityLocation.clone().add(0, maxHeight - 0.5, 0), reward);
                    AnimationUtilsManager.rotateAndRemoveItemDisplay(itemDisplay, 5, entity);
                    cancel();

                    new BukkitRunnable() {
                        double delay = 0;

                        @Override
                        public void run() {
                            if (delay <= 2) {
                                delay += 1;
                            } else {
                                AnimationUtilsManager.setGameMode(player, GameMode.SURVIVAL);
                                player.getInventory().addItem(reward.getItem());
                                AnimationUtilsManager.PLAYERS_IN_SPECTATOR_MODE.remove(player.getName());
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 20);
                    return;
                }
                AnimationUtilsManager.generateCircularPattern(entityLocation.clone().add(0, currentY - entityLocation.getY() + 0.3, 0), crate.getAnimationProperties().getColor(), 30, 1f, 0.35D, 0);
                currentY += 0.1;
            }
        }.runTaskTimer(plugin, 0, 1);
    }

}
