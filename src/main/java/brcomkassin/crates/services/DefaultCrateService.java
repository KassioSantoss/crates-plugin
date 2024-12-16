package brcomkassin.crates.services;

import brcomkassin.crates.Crate;
import brcomkassin.crates.cache.CrateCache;
import brcomkassin.crates.manager.CrateManager;
import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginLogger;

import java.util.Map;

public class DefaultCrateService implements CrateService {

    private final CrateManager crateManager;
    private final CrateCache crateCache;

    public DefaultCrateService(CrateManager crateManager, CrateCache crateCache) {
        this.crateManager = crateManager;
        this.crateCache = crateCache;
    }

    @Override
    public void summonCrate(Location location, Crate crate) {
        ArmorStand box = location.getWorld().spawn(location.getBlock().getLocation(), ArmorStand.class);
        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(box);
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(crate.entityModel());

        if (activeModel == null) {
            throw new RuntimeException("Não foi encontrado nenhum modelo com esse id de caixa: " + crate.id());
        }

        box.setVisible(false);
        box.setInvulnerable(true);
        box.setCanMove(false);
        box.setSmall(false);
        activeModel.setHitboxScale(1);
        modeledEntity.addModel(activeModel, true);
        Location crateLocation =
                new Location(location.getWorld(), location.getX(), location.getY() + 1, location.getZ());
        crateCache.addCrateByLocation(crateLocation, crate);
        PluginLogger.getGlobal().info("salvo na posiçao Y == " + crateLocation.getY());
    }

    @Override
    public void openCrate(Entity entity, ItemStack item) {
        if (!crateManager.isCrateEntity(entity)) {
            PluginLogger.getGlobal().info("RETORNOU 1");
            return;
        }
        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(entity);
        if (modeledEntity == null) {
            PluginLogger.getGlobal().info("RETORNOU 2");
            return;
        }
        Map<String, ActiveModel> activeModels = modeledEntity.getModels();
        if (activeModels.isEmpty()) {
            PluginLogger.getGlobal().info("RETORNOU 3");
            return;
        }
        boolean keyItem = crateManager.isKeyItem(item);

        if (!keyItem) {
            PluginLogger.getGlobal().info("RETORNOU 4");
            return;
        }
        Crate crate = crateManager.getCrateFromItem(item);
        Crate crateTarget = crateCache.getCrateByLocation(entity.getLocation());
        PluginLogger.getGlobal().info(crateCache.getLocationCrateMap().toString());

        if (crate == null || !crate.id().equals(crateTarget.id())) {
            PluginLogger.getGlobal().info("ID DAS CAIXAS NAO CONDIZEM:");
            PluginLogger.getGlobal().info("CAIXA ALVO ->" + crateTarget);
            PluginLogger.getGlobal().info("CAIXA DO CACHE ->" + crate);

            return;
        }
        PluginLogger.getGlobal().info("ABRIU");
        ActiveModel activeModel = activeModels.get(crate.id());
        AnimationHandler animationHandler = activeModel.getAnimationHandler();
        animationHandler.playAnimation("open", 0.3, 0.3, 1, true);
    }

}
