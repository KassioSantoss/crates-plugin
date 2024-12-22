package brcomkassin.crates.services;

import brcomkassin.crates.CrateLocation;
import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.manager.CrateManager;
import brcomkassin.crates.rewards.Reward;
import brcomkassin.crates.rewards.RewardCalculator;
import brcomkassin.crates.rewards.RewardHandlerAnimation;
import brcomkassin.crates.rewards.cache.RewardCache;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class DefaultCrateService implements CrateService {

    private final CrateManager crateManager;
    private final CrateCache crateCache;
    private final RewardCache rewardCache;
    private final RewardCalculator rewardCalculator;

    public DefaultCrateService(CrateManager crateManager, CrateCache crateCache, RewardCache rewardCache) {
        this.crateManager = crateManager;
        this.crateCache = crateCache;
        this.rewardCache = rewardCache;
        this.rewardCalculator = new RewardCalculator(crateCache);
    }

    @Override
    public void summonCrate(Player player, Location location, Crate crate) {
        Location centeredLocation = location.getBlock().getLocation().add(0.5, 0, 0.5);
        CrateLocation crateLocation = new CrateLocation(centeredLocation.getX(), centeredLocation.getY(), centeredLocation.getZ());

        if (crateCache.getLocationCrateMap().containsKey(crateLocation)) return;

        ArmorStand box = centeredLocation.getWorld().spawn(centeredLocation, ArmorStand.class);
        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(box);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(crate.baseEntityModel());

        if (activeModel == null) {
            throw new RuntimeException("Não foi encontrado nenhum modelo com esse id de caixa: " + crate.nameSpace());
        }

        double deltaX = player.getLocation().getX() - centeredLocation.getX();
        double deltaZ = player.getLocation().getZ() - centeredLocation.getZ();
        float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));
        yaw -= 180.0f;

        box.setVisible(false);
        box.setInvulnerable(true);
        box.setCanMove(false);
        box.setSmall(false);
        box.setRotation(yaw, 0.0f);
        activeModel.setHitboxScale(1);
        modeledEntity.addModel(activeModel, true);
        crateCache.addCrateByLocation(crateLocation, crate);
        player.sendMessage("Caixa spawnada com sucesso!");
    }

    @Override
    public void openCrate(Player player, Entity entity, ItemStack item) {
        if (!crateManager.isCrateEntity(entity)) return;

        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity);
        if (modeledEntity == null) return;

        Map<String, ActiveModel> activeModels = modeledEntity.getModels();
        if (activeModels.isEmpty()) return;

        boolean keyItem = crateManager.isKeyItem(item);
        if (!keyItem) return;

        Crate crate = crateManager.getCrateFromKey(item);
        CrateLocation crateLocation = new CrateLocation(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());
        Crate crateTarget = crateCache.getCrateByLocation(crateLocation);

        if (crate == null || crateTarget == null) return;
        if (!crate.nameSpace().equals(crateTarget.nameSpace())) return;

        ActiveModel activeModel = activeModels.get(crate.baseEntityModel());
        AnimationHandler animationHandler = activeModel.getAnimationHandler();
        animationHandler.playAnimation(crate.animation(), 0.3, 0.3, 1, true);

        player.sendMessage("id da caixa: " + crate.id());
        player.sendMessage("cache da caixa: " + rewardCache.getReward(crate.id()));
        player.sendMessage("cache da caixa 2: " + crateCache.getCrateById(crate.id()));

        Reward reward = rewardCalculator.calculateReward(crate.id(), rewardCache);
        RewardHandlerAnimation.animReward(entity, reward.getItem());
        player.sendMessage("Caixa aberta com sucesso!");
    }

}
