package brcomkassin.crates.rewards.animation.effects;

import brcomkassin.CratesPlugin;
import brcomkassin.crates.Crate;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.RewardCalculator;
import brcomkassin.crates.rewards.animation.utils.AnimationUtilsManager;
import brcomkassin.crates.rewards.animation.utils.ModelEngineAnimationManager;
import brcomkassin.crates.rewards.animation.service.AnimationService;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

public class DefaultParticleEffect implements AnimationService {

    private final CratesPlugin plugin = CratesPlugin.getInstance();
    private final RewardCalculator calculator;

    public DefaultParticleEffect(RewardCalculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public void run(Player player, Entity crateEntity, Crate crate) {
        Reward reward = calculator.calculateReward(crate);
        runTask(player, crateEntity, crate, reward);
    }

    private void runTask(Player player, Entity crateEntity, Crate crate, Reward reward) {
        AnimationUtilsManager.positionThePlayer(player, crateEntity, () -> {

            player.getInventory().addItem(reward.getItem());
            ModelEngineAnimationManager.play(crateEntity, crate);
            ItemDisplay itemDisplay = AnimationUtilsManager.setItemDisplay(crateEntity.getLocation(), reward);
            AnimationUtilsManager.PLAYERS_IN_SPECTATOR_MODE.add(player.getName());

            AnimationUtilsManager.startRunnable(15, () -> {
                AnimationUtilsManager.moveItemDisplay(itemDisplay, 1.8D, 0.05D, true);
                AnimationUtilsManager.rotateAndRemoveItemDisplay(itemDisplay, 7, crateEntity);

                AnimationUtilsManager.startRunnable(70, () -> {
                    AnimationUtilsManager.setGameMode(player, GameMode.SURVIVAL);
                    AnimationUtilsManager.PLAYERS_IN_SPECTATOR_MODE.remove(player.getName());
                });
            });
        });
    }
}