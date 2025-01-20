package brcomkassin.crates.services;

import brcomkassin.crates.location.CrateLocation;
import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.manager.CrateManager;
import brcomkassin.crates.rewards.RewardCalculator;
import brcomkassin.crates.rewards.animation.RewardHandlerAnimation;
import brcomkassin.crates.rewards.cache.RewardCache;
import brcomkassin.utils.Config;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;

public class DefaultCrateService implements CrateService {

    private final Config locationConfiguration;
    private final CrateManager crateManager;
    private final CrateCache crateCache;
    private final RewardCalculator rewardCalculator;
    private final RewardHandlerAnimation rewardHandlerAnimation;

    public DefaultCrateService(Config locationConfiguration, CrateManager crateManager, CrateCache crateCache, RewardCache rewardCache) {
        this.locationConfiguration = locationConfiguration;
        this.crateManager = crateManager;
        this.crateCache = crateCache;
        this.rewardCalculator = new RewardCalculator(rewardCache);
        this.rewardHandlerAnimation = new RewardHandlerAnimation(rewardCalculator);
    }

    @Override
    public void summonCrate(Player player, Location location, Crate crate) {
        Location centeredLocation = location.getBlock().getLocation().add(0.5, 0, 0.5);
        CrateLocation crateLocation = CrateLocation.of(centeredLocation);
        if (crateCache.getLocationCrateMap().containsKey(crateLocation)) return;

        ArmorStand box = centeredLocation.getWorld().spawn(centeredLocation, ArmorStand.class);
        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(box);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(crate.getBaseEntityModel());

        if (activeModel == null) {
            throw new RuntimeException("Não foi encontrado nenhum modelo com esse id de caixa: " + crate.getNameSpace());
        }

        double deltaX = player.getLocation().getX() - centeredLocation.getX();
        double deltaZ = player.getLocation().getZ() - centeredLocation.getZ();
        float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));
        yaw -= 180.0f;

        box.setVisible(false);
        box.setInvulnerable(true);
        box.setCanMove(false);
        box.setSmall(true);
        box.setSmall(false);
        box.setRotation(yaw, 0.0f);
        modeledEntity.addModel(activeModel, true);
        crateCache.addCrateByLocation(crateLocation, crate);

        String s = crateLocation + ":" + crate.getId();

        List<String> locations = locationConfiguration.getStringList("crateLocations.locations");
        locations.add(s);

        locationConfiguration.set("crateLocations.locations", locations);
        locationConfiguration.saveConfig();
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
        final Location entityLocation = entity.getLocation();
        CrateLocation crateLocation = CrateLocation.of(entityLocation);
        Crate crateTarget = crateCache.getCrateByLocation(crateLocation);

        if (crate == null || crateTarget == null) return;
        if (!crate.getNameSpace().equals(crateTarget.getNameSpace())) return;

        ActiveModel activeModel = activeModels.get(crate.getBaseEntityModel());
        AnimationHandler animationHandler = activeModel.getAnimationHandler();
        animationHandler.playAnimation(crate.getAnimation(), 0.3, 0.3, 1, true);

        rewardHandlerAnimation.animReward(player, entity, crate);
        player.sendMessage("Caixa aberta com sucesso!");
    }

}
