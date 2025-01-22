package brcomkassin.crates.services;

import brcomkassin.CratesPlugin;
import brcomkassin.crates.Crate;
import brcomkassin.crates.manager.CrateManager;
import brcomkassin.crates.rewards.RewardCalculator;
import brcomkassin.crates.rewards.animation.RewardHandlerAnimation;
import brcomkassin.crates.rewards.cache.RewardCache;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class DefaultCrateService implements CrateService {

    private final CrateManager crateManager;
    private final RewardCalculator rewardCalculator;
    private final RewardHandlerAnimation rewardHandlerAnimation;

    public DefaultCrateService(CrateManager crateManager, RewardCache rewardCache) {
        this.crateManager = crateManager;
        this.rewardCalculator = new RewardCalculator(rewardCache);
        this.rewardHandlerAnimation = new RewardHandlerAnimation(rewardCalculator);
    }

    @Override
    public void summonCrate(Player player, Location location, Crate crate) {
        Location centeredLocation = location.getBlock().getLocation().add(0.5, 0, 0.5);

        if (!location.getNearbyEntities(1, 1, 1).isEmpty()) return;

        ArmorStand box = (ArmorStand) centeredLocation.getWorld().spawnEntity(centeredLocation, crate.getEntityType());

        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(box);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(crate.getBaseEntityModel());

        if (activeModel == null) {
            throw new RuntimeException("Não foi encontrado nenhum modelo com esse id de caixa: " + crate.getId());
        }

        double deltaX = player.getLocation().getX() - centeredLocation.getX();
        double deltaZ = player.getLocation().getZ() - centeredLocation.getZ();
        float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));
        yaw -= 180.0f;

        box.setInvisible(true);
        box.setInvulnerable(true);
        box.setCanMove(false);
        box.setRotation(yaw, 0.0f);
        box.setPersistent(true);

        box.getPersistentDataContainer().set(new NamespacedKey(CratesPlugin.getInstance(),
                crate.getNameSpace()), PersistentDataType.STRING, crate.getNameSpace());

        activeModel.setHitboxVisible(true);
        activeModel.setHitboxScale(0.9);

        modeledEntity.addModel(activeModel, true);

        int itemAmount = player.getInventory().getItemInMainHand().getAmount() - 1;
        if (itemAmount > 1) {
            player.getInventory().getItemInMainHand().setAmount(itemAmount);
        } else {
            player.getInventory().remove(crate.getCrateItem());
        }
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

        Crate crate = crateManager.getCrate(item);

        NamespacedKey namespacedKey = new NamespacedKey(CratesPlugin.getInstance(), crate.getNameSpace());

        if (!entity.getPersistentDataContainer().has(namespacedKey)) return;

        ActiveModel activeModel = activeModels.get(crate.getBaseEntityModel());
        AnimationHandler animationHandler = activeModel.getAnimationHandler();
        animationHandler.playAnimation(crate.getAnimation(), 0.3, 0.3, 1, true);

        int itemAmount = item.getAmount() - 1;
        if (itemAmount > 0) {
            item.setAmount(itemAmount);
        } else {
            player.getInventory().remove(item);
        }
        rewardHandlerAnimation.animReward(player, entity, crate);
        player.sendMessage("Caixa aberta com sucesso!");
    }

}
